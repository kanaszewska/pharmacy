package com.debska.pharmacy.dto.reqDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqOrderDTO {

    private List<Integer> orderedDrugsId;

    private int userId;
}
