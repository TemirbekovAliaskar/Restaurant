package java12.dto.request;

import java12.entity.MenuItem;

import java.util.Date;

public record StopRequest( String reason, Date date) {
}
