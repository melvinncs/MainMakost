/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komponen;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author dF
 */
public class TableActionCellEditor extends DefaultCellEditor{
    
    
    public TableActionEvent event;
    
    public TableActionCellEditor(TableActionEvent event){
        super(new JCheckBox());
        this.event = event;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
         PanelAction action =new PanelAction();
         action.initEvent(event, row);
         action.setBackground(table.getSelectionBackground());
         return action;
    }
    
}
