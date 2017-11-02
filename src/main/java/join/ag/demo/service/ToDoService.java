package join.ag.demo.service;

import join.ag.demo.dto.ToDoItemAddRequest;
import join.ag.demo.dto.ToDoItemUpdateRequest;
import join.ag.demo.model.ToDoItem;
import join.ag.demo.repository.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class ToDoService {

    @Autowired
    private ToDoItemRepository repository;

    @Transactional
    public ToDoItem save(ToDoItemAddRequest item) {

        ToDoItem toSave = new ToDoItem(item.getText());
        toSave.setCreatedAt(new Date());
        toSave.setCompleted(false);
        return repository.save(toSave);
    }

    @Transactional(readOnly = true)
    public Optional<ToDoItem> loadById(long id) {
        return ofNullable(repository.findOne(id));
    }

    @Transactional
    public Optional<ToDoItem> update(long id, ToDoItemUpdateRequest item) {
        return loadById(id).map(existing -> {
            existing.setText(item.getText());
            existing.setCompleted(item.isCompleted());
            return repository.save(existing);
        });
    }

}
