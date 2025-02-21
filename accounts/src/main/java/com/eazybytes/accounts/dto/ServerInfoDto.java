package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(name = "ServerInfo",
        description = "Server Connection information"
)

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerInfoDto {
    @Schema(
        description = "Server Service Host Name"
    )
    private String hostname;

    @Schema(
        description = "Server Connection IP Adress"
    )
    private String ip;

    @Schema(
        description = "Server Connection PORT Number"
    )
    private int port;

}
