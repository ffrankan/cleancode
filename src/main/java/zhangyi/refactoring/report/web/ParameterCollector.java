package zhangyi.refactoring.report.web;

import zhangyi.refactoring.report.engine.*;
import zhangyi.refactoring.report.servlet.ServletHttpRequest;

public class ParameterCollector {
    public void fillParameters(ServletHttpRequest request, ParameterGraph parameterGraph) {
        for (Parameter para : parameterGraph.getParmaeters()) {
            if (para instanceof SimpleParameter) {
                fillSimpleParameter(request, (SimpleParameter) para);
            } else {
                if (para instanceof ItemParameter) {
                    fillItemParameter(request, (ItemParameter) para);
                } else {
                    fillTableParameter(request, (TableParameter) para);
                }
            }
        }
    }

    private void fillTableParameter(ServletHttpRequest request, TableParameter para) {
        TableParameter tablePara = para;
        String[] rows =
                request.getParameterValues(tablePara.getRowName());
        String[] columns =
                request.getParameterValues(tablePara.getColumnName());
        String[] dataCells =
                request.getParameterValues(tablePara.getDataCellName());

        int columnSize = columns.length;
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < columns.length; j++) {
                TableParameterElement element = new TableParameterElement();
                element.setRow(rows[i]);
                element.setColumn(columns[j]);
                element.setDataCell(dataCells[columnSize * i + j]);
                tablePara.addElement(element);
            }
        }
    }

    private void fillItemParameter(ServletHttpRequest request, ItemParameter para) {
        ItemParameter itemPara = para;
        for (Item item : itemPara.getItems()) {
            String[] values = request.getParameterValues(item.getName());
            item.setValues(values);
        }
    }

    private void fillSimpleParameter(ServletHttpRequest request, SimpleParameter para) {
        SimpleParameter simplePara = para;
        String[] values = request.getParameterValues(simplePara.getName());
        simplePara.setValue(values);
    }
}
