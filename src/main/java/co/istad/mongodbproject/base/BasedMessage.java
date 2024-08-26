package co.istad.mongodbproject.base;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasedMessage {
    private String message;
}