package com.cg.service.color;

import com.cg.model.Color;
import com.cg.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColorService implements IColorService{

    @Autowired
    private ColorRepository colorRepository;

    @Override
    public List<Color> findAll() {
        return colorRepository.findAll() ;
    }

    @Override
    public Optional<Color> findById(Long id) {
        return Optional.empty();
    }

    public Optional<Color> findById(Integer id) {
        return colorRepository.findById(id);
    }

    @Override
    public Color getById(Long id) {
        return null;
    }

    @Override
    public Color save(Color color) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }
}
