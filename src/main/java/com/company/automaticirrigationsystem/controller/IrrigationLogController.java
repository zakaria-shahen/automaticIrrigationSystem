package com.company.automaticirrigationsystem.controller;

import com.company.automaticirrigationsystem.dto.IrrigationLogDto;
import com.company.automaticirrigationsystem.mapper.IrrigationLogMapper;
import com.company.automaticirrigationsystem.service.IrrigationLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/irrigation_log")
@RequiredArgsConstructor
@Slf4j
public class IrrigationLogController {

    private final IrrigationLogService irrigationLogService;
    private final IrrigationLogMapper irrigationLogMapper;

    @GetMapping
    public List<IrrigationLogDto> getAll() {
        log.debug("visited GET `{}` path", "/irrigation_log");
        return irrigationLogMapper.irrigationLogToDto(
                irrigationLogService.findAll()
        );
    }

    @GetMapping("/{id}")
    public IrrigationLogDto getOne(@PathVariable Long id) {
        log.debug("visited GET `{}` path", "/irrigation_log/{id}");
        return irrigationLogMapper.irrigationLogToDto(
                irrigationLogService.findById(id)
        );
    }

    @PostMapping
    public IrrigationLogDto post(@Valid @RequestBody IrrigationLogDto irrigationLogDto) {
        log.debug("visited POST `{}` path", "/irrigation_log");

        log.debug("Updating Slot Status by IoT");
        var irrigationLog =  irrigationLogService.saveIrrigationLogAndFindSlot(
                irrigationLogMapper.dtoToIrrigationLog(irrigationLogDto)
        );
        return irrigationLogMapper.irrigationLogToDto(irrigationLog);
    }

}
