package com.hbhb.cw.flowcenter.web.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowExportVO implements Serializable {

    private static final long serialVersionUID = 2958826464578777991L;

    @ExcelIgnore
    @Schema(description = "节点id")
    private String flowNodeId;

    @ColumnWidth(20)
    @ExcelProperty(value = "单位名称", index = 0)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "单位名称")
    private String unitName;

    @ColumnWidth(20)
    @ExcelProperty(value = "流程编号", index = 1)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "流程编号")
    private Long flowId;

    @ColumnWidth(20)
    @ExcelProperty(value = "流程名称", index = 2)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "流程名称")
    private String flowName;

    @ColumnWidth(40)
    @ExcelProperty(value = "节点名称", index = 3)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "节点名称")
    private String flowNodeName;

    @ExcelIgnore
    @Schema(description = "环节名称")
    private String linkName;

    @ColumnWidth(20)
    @ExcelProperty(value = "节点角色", index = 5)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "节点角色")
    private String roleDescription;

    @ColumnWidth(20)
    @ExcelProperty(value = "默认用户", index = 6)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "默认用户")
    private String userName;

    @ColumnWidth(20)
    @ExcelProperty(value = "角色范围", index = 7)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "角色范围")
    private String roleRange;

    @ColumnWidth(20)
    @ExcelProperty(value = "分配者", index = 8)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "分配者")
    private String roleLid;

    @ColumnWidth(40)
    @ExcelProperty(value = "过滤条件", index = 9)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "过滤条件")
    private String enableCond;
}
