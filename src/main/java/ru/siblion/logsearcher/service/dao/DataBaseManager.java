package ru.siblion.logsearcher.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.siblion.logsearcher.service.model.request.SearchInfo;
import ru.siblion.logsearcher.service.model.request.SignificantDateInterval;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataBaseManager implements Serializable, DaoService {

    @Autowired
    private DataSource dataSource;

    private Connection connection;

    public void setConnection() throws SQLException {
        connection = dataSource.getConnection();
    }

    private void closeConnection() throws SQLException {
        connection.close();
    }

    public void saveCreatedFile(SearchInfo searchInfo, String name) {
        try {
            setConnection();
            String addFileQuery = "INSERT INTO createdfiles"
                    + "(FileName, Extension, RegularExpression, Location) VALUES "
                    + "(?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(addFileQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, searchInfo.getFileExtension().toString());
            preparedStatement.setString(3, searchInfo.getRegularExpression());
            preparedStatement.setString(4, searchInfo.getLocation());
            preparedStatement.execute();

            addFileQuery = "INSERT INTO dateintervals"
                    + "(FileName, DateFrom, DateTo) VALUES "
                    + "(?, ?, ?)";

            preparedStatement = connection.prepareStatement(addFileQuery);
            for (SignificantDateInterval significantDateInterval : searchInfo.getDateInterval()) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, significantDateInterval.getDateFromString());
                preparedStatement.setString(3, significantDateInterval.getDateToString());
                preparedStatement.execute();
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeCreatedFile(String fileName) {
        try {
            setConnection();
            String removeOutdatedFileQuery = "DELETE FROM dateintervals "
                    + "WHERE FileName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(removeOutdatedFileQuery);
            preparedStatement.setString(1, fileName);
            preparedStatement.executeUpdate();

            removeOutdatedFileQuery = "DELETE FROM createdfiles "
                    + "WHERE FileName = ?";
            preparedStatement = connection.prepareStatement(removeOutdatedFileQuery);
            preparedStatement.setString(1, fileName);
            preparedStatement.executeUpdate();
            closeConnection();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getFilteredExistingFiles(SearchInfo searchInfo)  {
        try {
            setConnection();
            List<String> filesName = new ArrayList<>();
            String location = searchInfo.getLocation();
            String regularExpression = searchInfo.getRegularExpression();
            String fileExtension = searchInfo.getFileExtension().toString();
            String getExistingFiles = "SELECT FileName FROM createdfiles "
                    + "WHERE RegularExpression = ? AND Location = ? AND Extension = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(getExistingFiles);
            preparedStatement.setString(1, regularExpression);
            preparedStatement.setString(2, location);
            preparedStatement.setString(3, fileExtension);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                filesName.add(resultSet.getString(1));
            }
            closeConnection();
            return filesName;
        }

        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SignificantDateInterval> getDateIntervals(String fileName) {

        try {
            setConnection();
            List<SignificantDateInterval> existingFilesDateIntervals = new ArrayList<>();
            String dateIntervals = "SELECT DateFrom, DateTo FROM dateintervals "
                    + "WHERE FileName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(dateIntervals);
            preparedStatement.setString(1, fileName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SignificantDateInterval temp = new SignificantDateInterval();
                temp.setDateFrom(resultSet.getString(1));
                temp.setDateTo(resultSet.getString(2));
                existingFilesDateIntervals.add(temp);
            }
            closeConnection();
            return existingFilesDateIntervals;
        }

        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataBaseManager() {
    }


}
