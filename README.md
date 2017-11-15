## 1.来源
关于为什么会有ExcelBean工具包，先看看正常情况我们是如何处理一份Excel的导入。<br>
#### 正常逻辑
1. Excel转为BusinessBean。
2. 清洗或补充BusinessBean。
3. 将BusinessBeean导入数据库中，并完成其他业务逻辑或者回滚。
4. 如果有多份Excel，则重复执行步骤1到步骤3。<br>
*缺点：Excel直接转为BusinessBean，这将导入的过程和业务强耦合。一旦业务发生变化，操作Excel也发生变化。*
*解决方案：在Excel和BusinessBean加一个ExcelBean。*
#### 优化后逻辑
1. Excel转为ExcelBean。
2. ExcelBean转为BusinessBean，同时完成清洗或补充。
3. 将BusinessBeean导入数据库中，并完成其他业务逻辑或者回滚。
4. 如果有多份Excel，则重复执行步骤1到步骤3。<br>
*优点：一旦业务造成BusinessBean发生变化，只需要在清洗或补充BusinessBean时处理即可。*
## 2.表类型
ExcelBean将表类型分别四中，分别是：基本表，横表，竖表，混合表。
#### 基本表
Sheet中字段只包含简单的单值字段。一般对应一个bean中值只包含简单的String类型。
#### 横表
Sheet中每一行的值都符合相同的类型。一般对应一个list，list中每个对象只包含简单的String类型。
#### 竖表
Sheet中每一列的值都符合相同的类型。一般对应一个list，list中每个对象只包含简单的String类型。
#### 混合表
Sheet中存在多种表类型，可包括基本表，横表，竖表。
## 3.解析模式
ExcelBean一共包含两种解析模式，分别是xml模式和annotation模式。
### annotation模式
该模式下，主要使用四个注解。
```java
public @interface Excel {
	/** excel名称*/
	String name();
}
```
**name**为当前Excel的名称，建议填写*default*。
```java
public @interface Sheet {
	/** sheet名称*/
	String name();
	/** sheet类型
	SheetType type();
	/** 数据开始行数*/
	int startLine() default -1;
	/** 数据开始列数*/
	char startColumn() default 0;
	/** 退出处理器*/
	Class<? extends ExistProcessor> existProcessor() default ExistProcessor.class;
}
```
**type**为Sheet类型，包含``` BASIC```基本类型，```VERTICAL```竖表类型，```HORIZONTAL```横表类型，```MIXED```混合表类型。<br>
**existProcessor**为退出处理器类型，在**type**为```VERTICAL```或```HORIZONTAL```使用，用于判断行或列是否扫描结束。
```java
public @interface Cell {
	/** 单元格名称*/
	String name() default "";
	/** X开始*/
	char startX() default 0;
	/** X结束*/
	char endX() default 0;
	/** Y开始*/
	int startY() default -1;
	/** Y结束*/
	int endY() default -1;
	/** 值校验器*/
	Validator[] validators() default {};
	/** 值映射器*/
	Class<? extends MappingProcessor> mappingProcessor() default SingleStringMappingProcessor.class;
	/** 单元格类型*/
	CellType cellType() default CellType.SINGLEVALUE;
	/** 退出处理器*/
	Class<? extends ExistProcessor> existProcessor() default ExistProcessor.class;
	/** 坐标处理器*/
	Class<? extends PositionProcessor> positionProcessor() default PositionProcessor.class;
}
```
**starX**，**endX**，**startY**，**endY**和**positionProcessor**构成了单元格的位置，其中**positionProcessor**的优先级靠后。<br>
**validators**校验器列表，可对单元格值做校验。<br>
**mappingProcessor**值映射处理器，一般用于解析Excel中的单选框，下拉框，多选框等特殊值。<br>
**cellType**单元格类型，包括```SINGLEVALUE```单值模式，```VERTICAL```竖表模式，```HORIZONTAL```横表模式，```MIXED```混合表模式（暂未实现）。
```java
public @interface Validator {
	/** 校验器类型 **/
	Class<? extends ValidateProcessor> type();
	/** 校验器参数 */
	String param() default "";
}
```
**type**为校验器的class类型。<br>
**param**为校验器的参数。
### xml模式
待补充
## 4.处理器
工具包中包含了```ExistProcessor```，```MappingProcessor```，```PositionProcessor```，```ValidateProcessor```，共四大类处理器。
### ExistProcessor退出处理器
退出处理器是在横表或竖表的情况下使用，用于识别当前行或列是否结束。<br>
工具包提供了两种简单的实现：```SimpleHorizontalExistProcessor```简单横表退出处理器，```SimpleVerticalExistProcessor```简单竖表退出处理器。
### MappingProcessor映射处理器
映射处理器用于处理单元格值。<br>
工具包提供：```SingleStringMappingProcesso```r简单字符串映射器，```AbstractCheckboxMappingProcessor```多选框映射器，```AbstractDropdownMappingProcessor```下拉框映射器，```AbstractRadioboxMappingProcessor```单选框映射器。
### PositionProcessor位置处理器
待补充
### ValidateProcessor校验处理器
校验处理器用于处理单元格值校验。
工具包提供：```NotBlankValidateProcessor```非空校验器，```RegularValidateProcessor```正则校验器。
## 5.进阶：框架的基本解析流程
待补充
## 6.进阶：框架的扩展点
待补充
## 7.未完成的任务
1. 暂不支持CellType为MIXED模式。