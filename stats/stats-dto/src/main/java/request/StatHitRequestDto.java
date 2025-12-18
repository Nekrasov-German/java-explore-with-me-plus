package request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatHitRequestDto {
	/*
	Используется при вызове эндпоинта /hit
	Сохранение информации о том, что на uri конкретного сервиса был отправлен запрос пользователем.
	Название сервиса(app), uri и ip пользователя берутся из тела запроса.
	 */

	@NotBlank
	String app;

	@NotBlank
	String uri;

	@NotBlank
	String ip;

	@NotNull
	LocalDateTime timestamp;
}
