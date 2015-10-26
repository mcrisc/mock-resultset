
MockResultSet
=============

MockResultSet is an implementation of [java.sql.ResultSet](http://docs.oracle.com/javase/8/docs/api/java/sql/ResultSet.html) backed by CSV data. It was conceived to overcome infrastructure limitations such as database server or VPN unavailability.

MockResultSet allows programmers to go ahead with work even without a database server to run SQL queries. If a CSV file containing query results is available, a database connection can be easily emulated so that system layers depending on the data (e.g., reports) can still be developed and tested.


### Example

Suppose some `SELECT` statement results in the this data, stored in `data.csv`.
```csv
"id","parent_id","creation_date","name","balance"
8,,2000-03-01,"John McHidden",310
9,,2005-04-01,"Jane McFound",-256
10,,2007-10-01,"von Hidden, Stuart",180
```


If the database server is unreachable, a `ResultSet` can be mocked using the CSV data, as in the code bellow.
```java
ResultSet rs = new MockResultSet(
				new InputStreamReader(new FileInputStream("data.csv")));


while (rs.next()) {

  double balance = rs.getDouble("balance");
  Date date = rs.getDate("creation_date");

  if (balance > 0) {
    System.out.println(date + ": " + balance);
  }
}

rs.close();
```

When the server turns available, adapting the working code is a simple matter of replacing `MockResultSet` with the object returned by the JDBC driver.

---
### License
MockResultSet is Free and Open Source Software released under the MIT License.
