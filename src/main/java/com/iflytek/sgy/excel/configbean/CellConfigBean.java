package com.iflytek.sgy.excel.configbean;

import java.util.List;

import com.iflytek.sgy.excel.processer.MappingProcessor;
import com.iflytek.sgy.excel.processer.PositionProcessor;

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

	Class<? extends PositionProcessor> startXPositionProcesser;

	Class<? extends PositionProcessor> endXPositionProcesser;

	Class<? extends PositionProcessor> startYPositionProcesser;

	Class<? extends PositionProcessor> endYPositionProcesser;

	List<ValidatorConfigBean> validatorBeanConfigList;
}
