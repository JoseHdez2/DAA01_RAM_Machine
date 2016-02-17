package util;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class MyTableModel extends AbstractTableModel{
    
    TokenizedLines dataMatrix = null;
    
    public MyTableModel(TokenizedLines tl){
        this.dataMatrix = tl;
    }
    
    @Override
    public int getRowCount() {
        return dataMatrix.size();
    }

    @Override
    public int getColumnCount() {
        // TODO: We assume that all rows have same number of elements, without checking.
        if (getRowCount() > 0)
            return dataMatrix.get(0).size();
        else
            return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return dataMatrix.get(rowIndex).get(columnIndex);
    }

}
