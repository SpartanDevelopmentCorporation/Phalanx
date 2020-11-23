package sh.spartan.crud.phalanx.controller;

import sh.spartan.crud.phalanx.service.BaseService;
import sh.spartan.crud.phalanx.utils.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/")
public abstract class BaseController {

    public BaseController (BaseService service) {
        this.service = service;
    }

    protected final BaseService service;

    /**
     *
     * Endpoint that gets an object by its id
     *
      * @param id of the object to be retrieved
     * @return ResponseEntity object with the retrieved object
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<Response<?>> get(@PathVariable Long id) {
        return service.get(id);
    }

    /**
     *
     * Endpoint that deletes an object by its id
     *
     * @param id of the object to be deleted
     * @return ResponseEntity object with a String confirmation message
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    /**
     *
     * Endpoint that add or update an object
     *
     * @param payload incoming payload of an structured object to be persisted in json format
     * @return ResponseEntity object with the new persisted object with its id
     */
    @PostMapping(value = "/save")
    public ResponseEntity<Response<?>> save(@RequestBody String payload) {
        return service.save(payload);
    }

    /**
     *
     * Endpoint that get list of all objects
     *
     * @return ResponseEntity object with a List of elements
     */
    @GetMapping(value = { "", "/" })
    public ResponseEntity<Response<?>> getAll() {
        return service.getAll();
    }

    /**
     *
     * Endpoint that get list of objects in a paged sort
     *
     * @param page number of page to retrieve
     * @param size number of elements to get by page
     * @return ResponseEntity object with a List of elements
     */
    @GetMapping(value ="/page/{page}/{size}")
    public ResponseEntity<Response<?>> getAllPage(
            @PathVariable(name="page") Long page,
            @PathVariable(name="size") Long size
    ) {
        return service.getAllPage(page, size);
    }
}