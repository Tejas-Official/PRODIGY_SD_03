/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactmanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AdvancedContactManagementSystem {

    private JFrame frame;
    private List<Contact> contacts;
    private JList<Contact> contactList;
    private DefaultListModel<Contact> listModel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new AdvancedContactManagementSystem();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AdvancedContactManagementSystem() {
        frame = new JFrame("Advanced Contact Management System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        contacts = new ArrayList<>();
        listModel = new DefaultListModel<>();

        JPanel headerPanel = createHeaderPanel();
        frame.add(headerPanel, BorderLayout.NORTH);

        JPanel contactPanel = createContactPanel();
        frame.add(contactPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel();
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));

        JLabel titleLabel = new JLabel("PRODIGY INFOTECH");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel);
        return headerPanel;
    }

    private JPanel createContactPanel() {
        JPanel contactPanel = new JPanel(new BorderLayout());
        contactPanel.setBackground(new Color(230, 230, 250));

        contactList = new JList<>(listModel);
        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactList.setFont(new Font("Arial", Font.PLAIN, 18));
        JScrollPane scrollPane = new JScrollPane(contactList);

        contactPanel.add(scrollPane, BorderLayout.CENTER);
        return contactPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(230, 230, 250));

        JButton addButton = new JButton("Add Contact");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });

        JButton editButton = new JButton("Edit Contact");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editContact();
            }
        });

        JButton deleteButton = new JButton("Delete Contact");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteContact();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        return buttonPanel;
    }

    private void addContact() {
        ContactDialog dialog = new ContactDialog(frame, "Add Contact", null);
        Contact newContact = dialog.showDialog();

        if (newContact != null) {
            contacts.add(newContact);
            listModel.addElement(newContact);
        }
    }

    private void editContact() {
        int selectedIndex = contactList.getSelectedIndex();

        if (selectedIndex != -1) {
            Contact selectedContact = listModel.getElementAt(selectedIndex);
            ContactDialog dialog = new ContactDialog(frame, "Edit Contact", selectedContact);
            Contact updatedContact = dialog.showDialog();

            if (updatedContact != null) {
                contacts.set(selectedIndex, updatedContact);
                listModel.setElementAt(updatedContact, selectedIndex);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a contact to edit.");
        }
    }

    private void deleteContact() {
        int selectedIndex = contactList.getSelectedIndex();

        if (selectedIndex != -1) {
            listModel.remove(selectedIndex);
            contacts.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a contact to delete.");
        }
    }

    private static class Contact {
        private String name;
        private String phoneNumber;
        private String email;

        public Contact(String name, String phoneNumber, String email) {
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.email = email;
        }

        @Override
        public String toString() {
            return "Name: " + name + " | Phone: " + phoneNumber + " | Email: " + email;
        }
    }

    private static class ContactDialog extends JDialog {
        private JTextField nameField;
        private JTextField phoneField;
        private JTextField emailField;
        private Contact contact;

        public ContactDialog(JFrame parent, String title, Contact existingContact) {
            super(parent, title, true);
            setSize(400, 300);
            setLocationRelativeTo(parent);
            setLayout(new GridLayout(4, 2));
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            nameField = new JTextField();
            phoneField = new JTextField();
            emailField = new JTextField();

            if (existingContact != null) {
                nameField.setText(existingContact.name);
                phoneField.setText(existingContact.phoneNumber);
                emailField.setText(existingContact.email);
            }

            add(new JLabel("Name:"));
            add(nameField);
            add(new JLabel("Phone:"));
            add(phoneField);
            add(new JLabel("Email:"));
            add(emailField);

            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    contact = new Contact(nameField.getText(), phoneField.getText(), emailField.getText());
                    dispose();
                }
            });

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    contact = null;
                    dispose();
                }
            });

            add(saveButton);
            add(cancelButton);
        }

        public Contact showDialog() {
            setVisible(true);
            return contact;
        }
    }
}
