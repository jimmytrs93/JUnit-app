package com.develop.torres.test.app.app_test.mapper;

import com.develop.torres.test.app.app_test.dto.CuentaDto;
import com.develop.torres.test.app.app_test.entities.CuentaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CuentaMapper {

    CuentaDto cuentaEntityToCuentaDto(CuentaEntity cuentaEntity);

    List<CuentaDto> cuentasEntityToCuentasDto(List<CuentaEntity> cuentaEntityList);
}
