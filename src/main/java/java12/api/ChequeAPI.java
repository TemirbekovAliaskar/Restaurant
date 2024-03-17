package java12.api;

import java12.dto.request.MenuItemCheckRequest;
import java12.dto.response.ChequeResponse;
import java12.dto.response.DefaultResponse;
import java12.dto.response.GetCheckResponse;
import java12.service.ChequeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cheque")
public class ChequeAPI {

    private final ChequeService chequeService;


    @PostMapping()
    public DefaultResponse save(@RequestBody MenuItemCheckRequest checkrequest) {
        return chequeService.save(checkrequest);
     }

    @PostMapping("/update/{cheId}")
    public DefaultResponse update(@PathVariable Long cheId,
                                  @RequestBody  MenuItemCheckRequest checkRequest) {
        return chequeService.update(cheId, checkRequest);
     }

    @PostMapping("/delete/{cheId}")
    public DefaultResponse delete(@PathVariable Long cheId) {
        return chequeService.delete(cheId);
    }

    @GetMapping("/all")
    public List<GetCheckResponse> all() {
        return chequeService.getAll();
    }
//
    @GetMapping("/findCheck/{cheId}")
    public GetCheckResponse findCheckById(@PathVariable Long cheId) {
        return chequeService.findCheckById(cheId);
    }


}