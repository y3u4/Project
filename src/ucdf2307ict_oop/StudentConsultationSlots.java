package ucdf2307ict_oop;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class StudentConsultationSlots extends javax.swing.JFrame {
    private String studentID;
    private String[] columnName = {"Day", "Time", "Lecturer"};
    private DefaultTableModel model = new DefaultTableModel();
    private int row = -1;
    private File lecSlotsFile = new File("lecturerSlots.txt");
    private File studentAppointmentFile = new File("studentAppointment.txt");
    
    public StudentConsultationSlots(String studentID) {
        this.studentID = studentID;
        model.setColumnIdentifiers(columnName);
        initComponents();
        
        // Check if file exists, if not, create it
        if (!studentAppointmentFile.exists()) {
            try {
                studentAppointmentFile.createNewFile();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error creating file: " + e.getMessage());
            }
        }
        
        loadAvailableSlots();
    }
    
    private void loadAvailableSlots() {
        // Clear the table first
        model.setRowCount(0);
        
        // Create a list to store all valid slots
        List<Object[]> validSlots = new ArrayList<>();
        
        try {
            // Step 1: Get current date for comparison
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE (dd/MM/yyyy),hh:mm a");
            
            // Step 2: Read all booked slots into a list
            List<String> bookedSlots = new ArrayList<>();
            if (studentAppointmentFile.exists()) {
                Scanner bookingScanner = new Scanner(studentAppointmentFile);
                while (bookingScanner.hasNextLine()) {
                    String line = bookingScanner.nextLine().trim();
                    if (!line.isEmpty()) {
                        bookedSlots.add(line);
                    }
                }
                bookingScanner.close();
            }
            
            // Step 3: Read and process available slots
            Scanner slotScanner = new Scanner(lecSlotsFile);
            while (slotScanner.hasNextLine()) {
                String line = slotScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        String day = parts[0].trim();
                        String time = parts[1].trim();
                        String lecturer = parts[2].trim();
                        
                        // Check if slot is already booked
                        boolean isBooked = false;
                        for (String bookedSlot : bookedSlots) {
                            if (bookedSlot.contains(day + "," + time + "," + lecturer)) {
                                isBooked = true;
                                break;
                            }
                        }
                        
                        if (!isBooked) {
                            try {
                                // Get the date and start time for comparison
                                String startTime = time.split(" - ")[0].trim();
                                Date slotDate = dateFormat.parse(day + "," + startTime);
                                
                                // Only add future slots
                                if (slotDate.after(currentDate)) {
                                    Object[] slotData = {day, time, lecturer, slotDate}; // Include date for sorting
                                    validSlots.add(slotData);
                                }
                            } catch (Exception e) {
                                System.out.println("Error parsing date for slot: " + line);
                            }
                        }
                    }
                }
            }
            slotScanner.close();
            
            // Step 4: Sort the slots by date and time
            Collections.sort(validSlots, new Comparator<Object[]>() {
                @Override
                public int compare(Object[] slot1, Object[] slot2) {
                    Date date1 = (Date) slot1[3];
                    Date date2 = (Date) slot2[3];
                    return date1.compareTo(date2);
                }
            });
            
            // Step 5: Add sorted slots to the table
            for (Object[] slot : validSlots) {
                model.addRow(new Object[]{slot[0], slot[1], slot[2]}); // Don't add the Date object
            }
            
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error loading slots: " + e.getMessage());
        }
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        availableSlotsTable = new javax.swing.JTable();
        logoutButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        bookSlotsButton = new javax.swing.JButton();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setBackground(new java.awt.Color(255, 51, 51));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jLabel1.setFont(new java.awt.Font("Modern No. 20", 1, 36)); // NOI18N
        jLabel1.setText("Available Slots");

        availableSlotsTable.setFont(new java.awt.Font("Modern No. 20", 0, 12)); // NOI18N
        availableSlotsTable.setModel(model);
        availableSlotsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                availableSlotsTableMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(availableSlotsTable);

        logoutButton.setBackground(new java.awt.Color(255, 0, 51));
        logoutButton.setFont(new java.awt.Font("Modern No. 20", 0, 12)); // NOI18N
        logoutButton.setForeground(new java.awt.Color(255, 255, 255));
        logoutButton.setText("Log out");
        logoutButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        backButton.setBackground(new java.awt.Color(153, 153, 153));
        backButton.setFont(new java.awt.Font("Modern No. 20", 1, 12)); // NOI18N
        backButton.setForeground(new java.awt.Color(255, 255, 255));
        backButton.setText("< Back");
        backButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        bookSlotsButton.setFont(new java.awt.Font("Modern No. 20", 0, 12)); // NOI18N
        bookSlotsButton.setText("Book an Appointment");
        bookSlotsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookSlotsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 844, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 23, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(bookSlotsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(377, 377, 377))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backButton)
                    .addComponent(logoutButton))
                .addGap(11, 11, 11)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(bookSlotsButton)
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void availableSlotsTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_availableSlotsTableMouseReleased
        row = availableSlotsTable.getSelectedRow();
    }//GEN-LAST:event_availableSlotsTableMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        StudentLogin sl = new StudentLogin();
        sl.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        StudentDashboard sd = new StudentDashboard(studentID);
        sd.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void bookSlotsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookSlotsButtonActionPerformed
        int row = availableSlotsTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a slot to book!");
            return;
        }

        String selectedDay = model.getValueAt(row, 0).toString();
        String selectedTime = model.getValueAt(row, 1).toString();
        String selectedLecturer = model.getValueAt(row, 2).toString();

        try {
            // Check if this is a reschedule
            File tempFile = new File("temp_reschedule.txt");
            if (tempFile.exists()) {
                // Read old appointment details
                Scanner sc = new Scanner(tempFile);
                String oldDetails = sc.nextLine();
                sc.close();
                tempFile.delete();

                // Write to studentReschedule.txt
                FileWriter fw = new FileWriter("studentReschedule.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(studentID + "," + oldDetails + "," + 
                        selectedDay + "," + selectedTime + "," + selectedLecturer + ",Pending\n");
                bw.close();

                // Remove the selected slot from lecturerSlots.txt
                List<String> remainingSlots = new ArrayList<>();
                try (Scanner fileScanner = new Scanner(lecSlotsFile)) {
                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine().trim();
                        String slotToRemove = selectedDay + "," + selectedTime + "," + selectedLecturer;
                        if (!line.equals(slotToRemove)) {
                            remainingSlots.add(line);
                        }
                    }
                }

                // Write back to lecturerSlots.txt
                try (FileWriter slotWriter = new FileWriter(lecSlotsFile);
                        BufferedWriter slotBufferedWriter = new BufferedWriter(slotWriter)) {
                    for (String slot : remainingSlots) {
                        slotBufferedWriter.write(slot + "\n");
                    }
                }

                // Show confirmation and redirect
                JOptionPane.showMessageDialog(this, "Reschedule request submitted successfully!");
                StudentReschedule sr = new StudentReschedule(studentID);
                sr.setVisible(true);
                this.dispose();

            } else {
                // Normal booking process
                // Check if student already has an appointment at this time
                boolean hasExistingAppointment = false;
                try (Scanner appointmentScanner = new Scanner(studentAppointmentFile)) {
                    while (appointmentScanner.hasNextLine()) {
                        String line = appointmentScanner.nextLine().trim();
                        if (!line.isEmpty()) {
                            String[] parts = line.split(",");
                            if (parts[0].equals(studentID) && 
                                parts[1].equals(selectedDay) && 
                                parts[2].equals(selectedTime)) {
                                hasExistingAppointment = true;
                                break;
                            }
                        }
                    }
                }

                if (hasExistingAppointment) {
                    JOptionPane.showMessageDialog(this, "You already have an appointment at this time!");
                    return;
                }

                // Add appointment to studentAppointment.txt
                FileWriter appointmentWriter = new FileWriter(studentAppointmentFile, true);
                try (BufferedWriter appointmentBufferedWriter = new BufferedWriter(appointmentWriter)) {
                    appointmentBufferedWriter.write(studentID + "," + selectedDay + "," +
                            selectedTime + "," + selectedLecturer + "\n");
                }

                // Remove the slot from lecturerSlots.txt
                List<String> remainingSlots = new ArrayList<>();
                try (Scanner slotScanner = new Scanner(lecSlotsFile)) {
                    while (slotScanner.hasNextLine()) {
                        String line = slotScanner.nextLine().trim();
                        String slotToRemove = selectedDay + "," + selectedTime + "," + selectedLecturer;
                        if (!line.equals(slotToRemove)) {
                            remainingSlots.add(line);
                        }
                    }
                }

                // Write back to lecturerSlots.txt
                try (FileWriter slotWriter = new FileWriter(lecSlotsFile);
                        BufferedWriter slotBufferedWriter = new BufferedWriter(slotWriter)) {
                    for (String slot : remainingSlots) {
                        slotBufferedWriter.write(slot + "\n");
                    }                                                                                                                           
                }

                // Remove the row from the table
                model.removeRow(row);

                JOptionPane.showMessageDialog(this, "Appointment booked successfully!");
                StudentAppointment sa = new StudentAppointment(studentID);
                sa.setVisible(true);
                this.dispose();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_bookSlotsButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StudentConsultationSlots.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentConsultationSlots.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentConsultationSlots.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentConsultationSlots.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new StudentConsultationSlots().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable availableSlotsTable;
    private javax.swing.JButton backButton;
    private javax.swing.JButton bookSlotsButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton logoutButton;
    // End of variables declaration//GEN-END:variables
}
