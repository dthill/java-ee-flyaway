package pgfsd.service;

import pgfsd.dao.AirlineDao;
import pgfsd.dao.UserDao;
import pgfsd.entities.User;

import java.util.List;

public class UserService {
    private final UserDao userDao = new UserDao();

    public String getAllUsers() {
        List<User> allUsers = userDao.getAllUsers();
        if (allUsers == null || allUsers.size() == 0) {
            return ("<p>No users found</p>");
        } else {
            StringBuilder result = new StringBuilder("<table><tr><td>name</td><td>email</td><td>password</td><td>admin</td><td>delete airline</td></tr>");
            for (User user : allUsers) {
                String deleteForm = String.format("<form action=\"users-admin.jsp\" method=\"post\">\n" +
                        "    <input type=\"hidden\" value=\"%s\" name=\"user-delete\" id=\"destination-delete\">\n" +
                        "    <input type=\"submit\" value=\"delete\">\n" +
                        "</form>", user.getEmail());
                result.append("<tr>")
                        .append("<td>")
                        .append(user.getName())
                        .append("</td>")
                        .append("<td>")
                        .append(user.getEmail())
                        .append("</td>")
                        .append("<td>")
                        .append(user.getPassword())
                        .append("</td>")
                        .append("<td>")
                        .append(user.getAdmin())
                        .append("</td>")
                        .append("<td>")
                        .append(deleteForm)
                        .append("</td>")
                        .append("</tr>");
            }
            result.append("</table>");
            return result.toString();
        }
    }

    public String addUser(User user) {
        if (user == null) {
            return null;
        }
        if (user.getEmail() == null || user.getEmail().equals("")) {
            return "User not valid. Provide a valid email";
        }
        if (user.getPassword() == null || user.getPassword().equals("")) {
            return "User not valid. Provide a valid password";
        }
        if (user.getName() == null || user.getName().equals("")) {
            return "User not valid. Provide a valid name";
        }
        boolean success = userDao.addUser(user);
        if (success) {
            return "User added/edited successfully.";
        }
        return "An error occurred adding/editing the user.";
    }

    public String deleteUser(User user) {
        if (user == null || user.getEmail() == null || user.getEmail().equals("")) {
            return "Not a valid user to delete.";
        }
        boolean success = userDao.deleteUser(user);
        if (success) {
            return "User has been removed.";
        }
        return "An error occurred deleting the user";
    }
}
