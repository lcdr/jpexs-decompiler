/*
 *  Copyright (C) 2013 JPEXS
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jpexs.decompiler.flash.gui;

import com.jpexs.decompiler.flash.configuration.Configuration;
import com.jpexs.decompiler.flash.configuration.ConfigurationItem;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author JPEXS
 */
public class AdvancedSettingsDialog extends AppDialog {

    /**
     * Creates new form AdvancedSettingsDialog
     */
    public AdvancedSettingsDialog() {
        initComponents();
        View.centerScreen(this);
        View.setWindowIcon(this);
        pack();
        
        configurationTable.setCellEditor(configurationTable.getDefaultEditor(null));
        
        Map<String, Field> fields = Configuration.getConfigurationFields();
        String[] keys = new String[fields.size()];
        keys = fields.keySet().toArray(keys);
        Arrays.sort(keys);
        for (String name : keys) {
            Field field = fields.get(name);
            DefaultTableModel model = (DefaultTableModel) configurationTable.getModel();
            try {
                ConfigurationItem item = (ConfigurationItem) field.get(null);
                String description = Configuration.getDescription(field);
                if (description == null) {
                    description = "";
                }
                Object defaultValue = Configuration.getDefaultValue(field);
                if (defaultValue != null) {
                    description += " (default: " + defaultValue + ")";
                }
                model.addRow(new Object[]{name, item.get(), description});
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                // Reflection exceptions. This should never happen
                throw new Error(ex.getMessage());
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        configurationTable = new com.jpexs.decompiler.flash.gui.EachRowRendererEditor();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/jpexs/decompiler/flash/gui/locales/AdvancedSettingsDialog"); // NOI18N
        setTitle(bundle.getString("advancedSettings.dialog.title")); // NOI18N
        setModal(true);
        setPreferredSize(new java.awt.Dimension(600, 400));

        configurationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Value", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(configurationTable);
        configurationTable.getColumnModel().getColumn(1).setCellEditor(configurationTable.getCellEditor());

        btnOk.setText("OK");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOk)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnCancel)
                    .addComponent(btnReset))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @SuppressWarnings("unchecked")
    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        TableModel model = configurationTable.getModel();
        int count = model.getRowCount();
        boolean modified = false;
        for (int i = 0; i < count; i++) {
            String name = (String) model.getValueAt(i, 0);
            Object value = model.getValueAt(i, 1);
            Map<String, Field> fields = Configuration.getConfigurationFields();
            Field field = fields.get(name);
            ConfigurationItem item = null;
            try {
                item = (ConfigurationItem) field.get(null);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                // Reflection exceptions. This should never happen
                throw new Error(ex.getMessage());
            }
            if (item.get() != null && !item.get().equals(value)) {
                item.set(value);
                modified = true;
            }
        }
        Configuration.saveConfig();
        setVisible(false);
        if (modified) {
            showRestartConfirmDialod();
        }
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        setVisible(false);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        Map<String, Field> fields = Configuration.getConfigurationFields();
        for (Entry<String, Field> entry : fields.entrySet()) {
            String name = entry.getKey();
            Field field = entry.getValue();
            try {
                ConfigurationItem item = (ConfigurationItem) field.get(null);
                item.unset();
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                // Reflection exceptions. This should never happen
                throw new Error(ex.getMessage());
            }
        }
        Configuration.saveConfig();
        setVisible(false);
        showRestartConfirmDialod();       
    }//GEN-LAST:event_btnResetActionPerformed

    private void showRestartConfirmDialod() {
        if (View.showConfirmDialog(this, translate("advancedSettings.restartConfirmation"), AppStrings.translate("message.warning"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            SelectLanguageDialog.reloadUi();
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnReset;
    private com.jpexs.decompiler.flash.gui.EachRowRendererEditor configurationTable;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
