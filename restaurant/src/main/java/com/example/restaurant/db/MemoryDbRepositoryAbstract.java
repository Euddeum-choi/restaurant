package com.example.restaurant.db;

import com.example.restaurant.db.MemoryDbEntity;
import com.example.restaurant.db.MemoryDbRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract public class MemoryDbRepositoryAbstract<T extends MemoryDbEntity> implements MemoryDbRepository<T> {

    private final List<T> db = new ArrayList<>();
    private int index = 0;

    @Override
    public Optional<T> findById(int index) {

        //와일드카드를 사용해서 memoryDbEntity를 상속받은 애들은 모두 getIndex사용가능
        //getIndex를 통해서 접근해서 매개변수로 들어온 index와 같으면 findFist 적용
        return db.stream().filter(it->it.getIndex() == index).findFirst();
    }

    @Override
    public T save(T entity) {

        var optionalEntity = db.stream().filter(it->it.getIndex() == entity.getIndex()).findFirst();

        if(optionalEntity.isEmpty()){
            //db에 데이터가 아직 없는 경우
            index++;
            entity.setIndex(index);
            db.add(entity);
            return entity;
        }else {
            //db에 이미 데이터가 있는 경우
            var preIndex = optionalEntity.get().getIndex();
            entity.setIndex(preIndex);

            deleteById(preIndex);
            db.add(entity);
            return entity;
        }
    }

    @Override
    public void deleteById(int index) {
        var optionalEntity = db.stream().filter(it-> it.getIndex() == index).findFirst();
        if(optionalEntity.isPresent()){
            db.remove(optionalEntity.get());
        }
    }

    @Override
    public List<T> listAll() {
        return db;
    }
}
