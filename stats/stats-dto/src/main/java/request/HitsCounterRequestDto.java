/*package request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HitsCounterRequestDto {
	*//*
	Используется при вызове эндпоинта /stats
	Получение статистики по посещениям.
	Дата и время начала диапазона за который нужно выгрузить статистику,
	Дата и время конца диапазона за который нужно выгрузить статистику,
	Список uri для которых нужно выгрузить статистику
	Нужно ли учитывать только уникальные посещения (только с уникальным ip)
	 *//*

	@NotNull
	LocalDateTime start;

	@NotNull
	LocalDateTime end;

	//@NotNull не нужен - не обязательно должен быть
	@Size(min = 1)
	List<String> uris;

	//@NotNull не нужен - если он не указан то по спецификации (defaultValue = "false")
	Boolean unique;
}*/
