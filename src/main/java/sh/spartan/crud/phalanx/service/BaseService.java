package sh.spartan.crud.phalanx.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import sh.spartan.crud.phalanx.utils.Response;
import sh.spartan.crud.phalanx.utils.Util;

@Service
public abstract class BaseService {

    private static final Logger LOG = LoggerFactory.getLogger(BaseService.class);

    public BaseService (PagingAndSortingRepository repository) {
        this.repository = repository;
    }

    protected Util util;

    protected ObjectMapper objectMapper;

    protected final PagingAndSortingRepository repository;

    protected Class<?> entity;

    public final void setEntity(Class<Object> entity) {
        this.entity = entity;
    }

    @Autowired
    private final void setUtil(Util util) {
        this.util = util;
    }

    @Autowired
    private final void setObjectMapper(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    /**
     *
     * Method that gets an object by its id
     *
     * @param id of the object to be retrieved
     * @return ResponseEntity object with the retrieved object
     */
    @SuppressWarnings("unchecked")
    public ResponseEntity<Response<?>> get(Long id) {

        ResponseEntity<Response<?>> state;
        try {
            state = util.getResponse(200, repository.findById(id), "OK", new Response<>());
        } catch (Exception e) {
            LOG.error("ERROR FINDING BY ID" , e);
            state = util.notifyError(e);
        }
        return state;
    }

    /**
     *
     * Method that add or update an object
     *
     * @param request incoming payload of an structured object to be persisted in json format
     * @return ResponseEntity object with the new persisted object with its id
     */
    public ResponseEntity<Response<?>> save(@RequestBody String request) {

        ResponseEntity<Response<?>> state;
        try {
            Object entityReq = objectMapper.readValue(request, entity);
            state = util.getResponse(200, repository.save(entityReq), "OK", new Response<>());
        } catch (Exception e) {
            LOG.error("ERROR SAVING" , e);
            state = util.notifyError(e);
        }

        return state;

    }

    /**
     *
     * Method that deletes an object by its id
     *
     * @param id of the object to be deleted
     * @return ResponseEntity object with a String confirmation message
     */
    public ResponseEntity<Response<?>> delete(@RequestBody Long id) {

        ResponseEntity<Response<?>> state;
        try {
            //noinspection unchecked
            repository.deleteById(id);

            state = util.getResponse(200, id, "OK", new Response<String>());

        } catch (Exception e) {
            LOG.error("ERROR DELETING" , e);
            state = util.notifyError(e);
        }

        return state;

    }

    /**
     *
     * Method that get list of all objects
     *
     * @return ResponseEntity object with a List of elements
     */
    public ResponseEntity<Response<?>> getAll() {

        ResponseEntity<Response<?>> state;
        try {
            state = util.getResponse(200, repository.findAll(), "OK", new Response<Object[]>());
        } catch (Exception e) {
            LOG.error("ERROR FINDING ALL" , e);
            state = util.notifyError(e);
        }
        return state;
    }

    /**
     *
     * Method that get list of objects in a paged sort
     *
     * @param page number of page to retrieve
     * @param size number of elements to get by page
     * @return ResponseEntity object with a List of elements
     */
    public ResponseEntity<Response<?>> getAllPage(Long page, Long size) {

        int tPage = page != null ? page.intValue() : 0;
        int tSize = size != null ? size.intValue() : 50;

        ResponseEntity<Response<?>> state;
        try {
            state = util.getResponse(200, repository.findAll(PageRequest.of(tPage, tSize)), "OK", new Response<Object[]>());
        } catch (Exception e) {
            LOG.error("ERROR FINDING BY PAGE" , e);
            state = util.notifyError(e);
        }
        return state;
    }
}