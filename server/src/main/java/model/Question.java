package model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Question {
    private int id;
    @NonNull
    String text;
    @NonNull
    String topic;
}
