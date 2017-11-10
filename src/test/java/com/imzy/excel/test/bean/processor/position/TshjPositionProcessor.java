package com.imzy.excel.test.bean.processor.position;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.imzy.excel.processer.PositionProcessor;
import com.imzy.excel.util.SheetUtils;

public class TshjPositionProcessor implements PositionProcessor {

	private Cell positionCell;

	private Cell getCell(Sheet sheet) {
		if (positionCell == null) {
			for (Row row : sheet) {
				Cell cell = row.getCell(0);
				if (StringUtils.equals(SheetUtils.getCellValue(cell), "特殊环节")) {
					positionCell = cell;
					break;
				}
			}
		}
		return positionCell;
	}

	@Override
	public Character getStartX(Sheet sheet) {
		return 'c';
	}

	@Override
	public Character getEndX(Sheet sheet) {
		Cell cell = getCell(sheet);
		return (char) (cell.getRow().getPhysicalNumberOfCells() - 1 + 'a');
	}

	@Override
	public Integer getStartY(Sheet sheet) {
		Cell cell = getCell(sheet);
		return cell.getRowIndex() + 1;
	}

	@Override
	public Integer getEndY(Sheet sheet) {
		Cell cell = getCell(sheet);
		return cell.getRowIndex() + 3;
	}

}
