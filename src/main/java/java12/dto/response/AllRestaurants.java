package java12.dto.response;

public record AllRestaurants(Long id,String name, String location,String restType,int servicePercent,int numberOfEmployees) {
}
