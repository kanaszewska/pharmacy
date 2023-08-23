package com.debska.pharmacy.dto.respDTO;

import com.debska.pharmacy.dto.DrugDTO;
import com.debska.pharmacy.dto.UserDTO;
import com.debska.pharmacy.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespOrderDTO {

    private Status status;

    private BigDecimal wholePrice;
    private List<DrugDTO> orderedDrugsDTO;
    private UserDTO user;
}
