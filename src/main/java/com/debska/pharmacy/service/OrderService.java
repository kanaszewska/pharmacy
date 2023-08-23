package com.debska.pharmacy.service;

import com.debska.pharmacy.dto.reqDTO.ReqOrderDTO;
import com.debska.pharmacy.dto.respDTO.RespOrderDTO;
import com.debska.pharmacy.entity.DrugEntity;
import com.debska.pharmacy.entity.OrderEntity;
import com.debska.pharmacy.entity.UserEntity;
import com.debska.pharmacy.enums.Status;
import com.debska.pharmacy.exceptions.NotFoundException;
import com.debska.pharmacy.mapper.OrderMapper;
import com.debska.pharmacy.repository.DrugRepository;
import com.debska.pharmacy.repository.OrderRepository;
import com.debska.pharmacy.repository.UserRepository;
import com.debska.pharmacy.validators.OrderValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderService {

    private OrderRepository orderRepository;

    private DrugRepository drugRepository;

    private UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, DrugRepository drugRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.drugRepository = drugRepository;
        this.userRepository = userRepository;
    }

    public RespOrderDTO createOrder(ReqOrderDTO reqOrderDTO) {
        List<DrugEntity> drugsListByIdIn = drugRepository.findDrugsByIdIn(reqOrderDTO.getOrderedDrugsId());

        Optional<UserEntity> userEntity = userRepository.findById(reqOrderDTO.getUserId());

        OrderValidator.validateSizeOfLists(drugsListByIdIn, reqOrderDTO.getOrderedDrugsId());

        RespOrderDTO respOrderDTO = OrderMapper.mapOrderEntityToDTO(saveNewOrder(drugsListByIdIn, userEntity.get()));

        return respOrderDTO;
    }

    private BigDecimal sumPrices(List<DrugEntity> listOfDrugs) {

        return listOfDrugs.stream()
                .map(x -> x.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private OrderEntity saveNewOrder(List<DrugEntity> drugsList, UserEntity userEntity) {
        OrderEntity orderEntity = OrderEntity.builder()
                .status(Status.CREATED)
                .wholePrice(sumPrices(drugsList))
                .orderedDrugsEntity(drugsList)
                .user(userEntity)
                .build();

        drugsList.stream()
                .forEach(drugEntity -> drugEntity.getOrders().add(orderEntity));
        orderRepository.save(orderEntity);

        return orderEntity;
    }

    public RespOrderDTO getOrderById(int id) {
        return orderRepository.findById(id)
                .map(OrderMapper::mapOrderEntityToDTO)
                .orElseThrow(() -> new NotFoundException("The order is not found"));

    }

    public List<RespOrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderEntity -> OrderMapper.mapOrderEntityToDTO(orderEntity))
                .collect(Collectors.toList());
    }

    public String deleteOrderById(int id) {
        OrderEntity foundOrderById = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("The order does not found!"));

        orderRepository.delete(foundOrderById);

        return "You have removed the order " + foundOrderById.getId();
    }

    public RespOrderDTO changeStatusOrderById(int id) {
        OrderEntity foundOrderById = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("The order does not found!"));

        foundOrderById.changeToTheNextStatus();
        orderRepository.save(foundOrderById);
        RespOrderDTO respOrderDTO = OrderMapper.mapOrderEntityToDTO(foundOrderById);

        return respOrderDTO;
    }

}