package com.lpnu.PZ.dto;

import com.lpnu.PZ.domain.Client;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDTO {
    private OrderDTO order;
    private String clientName;

    public static ClientDTO mapToClientDTO(final Client client) {
        final ClientDTO clientDTO = new ClientDTO();
        clientDTO.setClientName(client.getClientName());
        clientDTO.setOrder(OrderDTO.mapToOrderDTO(client.getOrder()));
        return clientDTO;
    }
}
