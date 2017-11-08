package com.imzy.excel.configbean;

import java.util.List;

import com.imzy.excel.processer.MappingProcessor;
import com.imzy.excel.processer.PositionProcessor;

import lombok.Data;

/**
 * cell配置bean
 * @author yangzhang7
 *
 */
@Data
public class CellConfigBean {

	String fieldName;

	String name;

	Character startX;

	Character endX;

	Integer startY;

	Integer endY;

	Class<? extends MappingProcessor> mappingProcessor;

	Class<? extends PositionProcessor> positionProcesser;

	List<ValidatorConfigBean> validatorBeanConfigList;
}
