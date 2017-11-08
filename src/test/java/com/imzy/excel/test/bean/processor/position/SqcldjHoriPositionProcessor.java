package com.imzy.excel.test.bean.processor.position;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.imzy.excel.processer.PositionProcessor;
import com.imzy.excel.util.SheetUtils;

public class SqcldjHoriPositionProcessor implements PositionProcessor {
	private Cell positionCell;

	private Cell getCell(Sheet sheet) {
		if (positionCell == null) {
			for (Row row : sheet) {
				Cell cell = row.getCell(0);
				if (StringUtils.equals(SheetUtils.getCellValue(cell), "姓名")) {
					positionCell = cell;
					break;
				}
			}
		}
		return positionCell;
	}

	@Override
	public Character getStartX(Sheet sheet) {
		return 'b';
	}

	@Override
	public Character getEndX(Sheet sheet) {
		return 'f';
	}

	@Override
	public Integer getStartY(Sheet sheet) {
		return 6;
	}

	@Override
	public Integer getEndY(Sheet sheet) {
		Cell cell = getCell(sheet);
		return cell.getRowIndex();
	}

}
