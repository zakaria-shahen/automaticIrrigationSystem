package com.company.automaticirrigationsystem.controller;

import com.company.automaticirrigationsystem.dto.SlotDto;
import com.company.automaticirrigationsystem.mapper.SlotMapper;
import com.company.automaticirrigationsystem.service.SlotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/slot")
@RequiredArgsConstructor
@Slf4j
public class SlotController {

    private final SlotService slotService;
    private final SlotMapper slotMapper;

    @GetMapping
    public List<SlotDto> getAll() {
        log.debug("visited GET `{}` path", "/slot");
        return slotMapper.slotToDto(
                slotService.findAll()
        );
    }

    @GetMapping("/{id}")
    public SlotDto getOne(@PathVariable Long id) {
        log.debug("visited GET `{}` path", "/slot/{id}");
        return slotMapper.slotToDto(
                slotService.findById(id)
        );
    }

    @PostMapping
    public SlotDto post(@RequestBody SlotDto slotDto) {
        log.debug("visited POST `{}` path", "/slot");
        var slot = slotService.create(slotMapper.dtoToSlot(slotDto));
        return slotMapper.slotToDto(slot);
    }

    @PatchMapping
    public SlotDto patch(@RequestBody SlotDto slotDto) {
        log.debug("visited PATCH `{}` path", "/slot");
        var slot = slotService.partialUpdate(slotMapper.dtoToSlot(slotDto));
        return slotMapper.slotToDto(slot);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.debug("visited DELETE `{}` path", "/slot/{id}");
        slotService.deleteById(id);
    }

}
