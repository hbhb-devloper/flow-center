package com.hbhb.cw.flowcenter.web.vo;

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

/**
 * Copyright (c) 2020 Choice, Inc. All Rights Reserved. Choice Proprietary and Confidential.
 *
 * @author jiyu@myweimai.com
 * @since 2020-08-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowRoleExportVO implements Serializable {

    private static final long serialVersionUID = -1797852518228196203L;

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
    @ExcelProperty(value = "流程角色名称", index = 1)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "流程角色名称")
    private String roleName;

    @ColumnWidth(20)
    @ExcelProperty(value = "用户名称", index = 2)
    @HeadFontStyle(fontHeightInPoints = 11, bold = false)
    @HeadStyle(fillPatternType = FillPatternType.NO_FILL, wrapped = false,
            borderLeft = BorderStyle.NONE, borderRight = BorderStyle.NONE,
            borderTop = BorderStyle.NONE, borderBottom = BorderStyle.NONE,
            verticalAlignment = VerticalAlignment.CENTER)
    @ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
    @Schema(description = "用户名称")
    private String nickName;
}
