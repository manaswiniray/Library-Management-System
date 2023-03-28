import java.sql.*;
import java.util.*;


public class data {
	static Connection connection;		//connects to database by creating objects
	
	public static void main(String[] args) {
     
		serverRoom();
        
    }
	
	private static void serverRoom() {
		setUpSQL();
		tableCreation();
		menu();
	}
	
	public static void menu() {
		System.out.println("LIBRARY MANAGEMENT SYSTEM: ");
		System.out.println("MENU: ");
		
		boolean exit=true;
		
		while(exit) {
			System.out.println("1. Add Student");
			System.out.println("2. Get Particular Student Details");
			System.out.println("3. Add Book");
			System.out.println("4. Get Particular Book Details");
			System.out.println("5. Issue Book");
			System.out.println("6. Return Book");
			System.out.println("7. See Student Details List: ");
			System.out.println("8. See Library Book Details List: ");
			System.out.println("9. See Librarian Record Book List:");
			System.out.println("10. Exit");
			
			
			int choice=userChoice();
			
			switch(choice) {
				case 1:
					addStudents();
					break;
				case 2:
					getStudentDetails();
					break;
				case 3:
					addBook();
 					break;
				case 4:
					getBookDetails();
					break;					
				case 5:
					issueBook();
					break;
				case 6:
					returnBook();
					break;
				case 7:
					printStudentDetails();
					break;
				case 8:
					printLibraryBookDetails();
					break;
				case 9:
					printLibrarianRecord();
					break;
				case 10:
					System.out.println("Thank You! Visit Again!!!!");
					exit=false;
					break;
			
			}
			
		}
	}
	
	public static void setUpSQL() {
		try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
	            System.out.println("✔️Driver");
	            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "pupupuaa8");	
	            System.out.println("✔️Connection");
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void tableCreation() {
		try {
           
            DatabaseMetaData metaData = connection.getMetaData();
            
            
            ResultSet resultSet = metaData.getTables(null, null, "librarianrecord", null);
            //resultSet.next();
            Statement statement = connection.createStatement();//helps in execution
            if (!resultSet.next()) {
               
                statement.execute("create table librarianRecord(TransactionId int primary key auto_increment, student_Id int, "
                		+ "BookId int, DateOfIssue date, ReturnDate date,Status varchar(10), Penalty int)auto_increment=9687");
                
                
                System.out.println("✔️ librarianRecord Table Created");
            }
            
           resultSet = metaData.getTables(null, null, "libraryBooks", null);
            //resultSet.next();
           statement = connection.createStatement();//helps in execution
            if (!resultSet.next()) {
               
                statement.execute("create table libraryBooks( BookId int not null primary key, NameOfBook varchar (50), "
                		+ "Publisher varchar(50), Section varchar(50), Cost smallint, Quantity tinyint)");
                System.out.println("✔️ libraryBooks Table Created");
            }
            
            resultSet = metaData.getTables(null, null, "studentdetails", null);
            //resultSet.next();
            statement = connection.createStatement();//helps in execution
            if (!resultSet.next()) {
               
                statement.execute("create table studentDetails( student_Id int not null primary key, "
                		+ "Name varchar(50), Branch varchar(5), Section varchar(1), Year smallint, Phone_No varchar(12),TotalIssued int not null)");
                
                System.out.println("✔️ studentdetails Table Created");
            }
            
            resultSet=statement.executeQuery("Select * from studentdetails");
            
         
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
		
	}
	
	public static int userChoice() {
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter choice: ");
		int choice=sc.nextInt();
		return choice;
		
	}
	
	public static void addStudents() {
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter Student ID: ");
		int student_Id=sc.nextInt();
		
		System.out.println("Enter Name: ");
		String name=sc.nextLine();
		
		System.out.println("Enter Branch: ");
		String branch=sc.next();
		
		System.out.println("Enter Section: ");
		String section=sc.next();
		
		System.out.println("Enter Year: ");
		int year=sc.nextInt();
		
		System.out.println("Enter Phone No.: ");
		String phone_no=sc.next();
		
		try {
			PreparedStatement ps=connection.prepareStatement("insert into studentdetails values(?,?,?,?,?,?,0)");
			ps.setInt(1, student_Id);
			ps.setString(2, name);
			ps.setString(3, branch);
			ps.setString(4, section);
			ps.setInt(5, year);
			ps.setString(6, phone_no);
			ps.executeUpdate();
			
			System.out.println("Student Details added");
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	
	public static void getStudentDetails() {
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter Student ID: ");
		
		int student_id=sc.nextInt();
		
		
		try {
			PreparedStatement ps=connection.prepareStatement("select * from studentdetails "
					+ "where student_Id=?");
				
			ps.setInt(1, student_id);
				
			ResultSet output=ps.executeQuery();
			output.next();
				
			System.out.println("Student ID: "+output.getInt(1));
			System.out.println("Student Name: "+output.getString(2));
			System.out.println("Branch: "+output.getString(3));
			System.out.println("Section: "+output.getString(4));
			System.out.println("Year: "+output.getInt(5));
			System.out.println("Phone No.: "+output.getString(6));
				
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void addBook() {
		Scanner sc=new Scanner(System.in);

		System.out.println("Enter Book_ID: ");
		int book_id=sc.nextInt();
		
		System.out.println("Enter Name of Book: ");
		String name=sc.nextLine();
		
		sc.next();
		
		System.out.println("Enter Publisher: ");
		String publisher=sc.nextLine();
		
		sc.next();
		
		System.out.println("Enter Section in Book is present: ");
		String section=sc.next();
		
		System.out.println("Enter Cost: ");
		int cost=sc.nextInt();
		
		System.out.println("Enter Quantity: ");
		int quantity=sc.nextInt();
		
		try {
			PreparedStatement ps=connection.prepareStatement("insert into libraryBooks values(?,?,?,?,?,?)");
			ps.setInt(1, book_id);
			ps.setString(2, name);
			ps.setString(3, publisher);
			ps.setString(4, section);
			ps.setInt(5, cost);
			ps.setInt(6, quantity);
			ps.executeUpdate();
			
			System.out.println("Book Details added");
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	
	public static void getBookDetails() {
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter Book ID: ");
		
		int book_id=sc.nextInt();
		
		
		try {
			PreparedStatement ps=connection.prepareStatement("select * from libraryBooks "
					+ "where BookId=?");
				
			ps.setInt(1, book_id);
				
			ResultSet output=ps.executeQuery();
			output.next();
				
			System.out.println("Book ID: "+output.getInt(1));
			System.out.println("Book Name: "+output.getString(2));
			System.out.println("Publisher Name: "+output.getString(3));
			System.out.println("Section in which book is present: "+output.getString(4));
			System.out.println("Cost of Book: "+output.getInt(5));
			System.out.println("Quantity present in the library: "+output.getInt(6));
			
			
				
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void issueBook() {
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter Student ID: ");
		int student_id=sc.nextInt();
		
		if(!studentExists(student_id)) {
			System.out.println("Not Registered!!!");
			return;
		}
		
		if(studentLimit(student_id)) {
			System.out.println("Issued Maximum No. of Books!!!");
			return;
		}
		
		System.out.println("Enter Book ID: ");
		int book_id=sc.nextInt();
		
		if(bookExists(book_id)) {
			System.out.println("Out of Stock!!!");
			return;
		}
		
		//Student Issued
		
		try {
			PreparedStatement ps=connection.prepareStatement("insert into librarianRecord(student_Id,BookId,DateOfIssue,Status)"
					+ " values(?,?,curdate(),'Issued')");
			ps.setInt(1,student_id );
			ps.setInt(2,book_id );
			ps.executeUpdate();
			
			System.out.println("Book issued Successfully! ");
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		//updating the totalIssue in studentdetails for a particular student
		
		try {
			PreparedStatement ps=connection.prepareStatement("update studentdetails set TotalIssued=TotalIssued+1 where student_Id=?");
			ps.setInt(1,student_id );
			ps.executeUpdate();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		
		
		//updating the quantity in librarianrecord
		
		try {
			PreparedStatement ps=connection.prepareStatement("update librarianRecord set Quantity=Quantity-1 where BookId=?");
			ps.setInt(1,book_id );
			ps.executeUpdate();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		
		//printing transaction+id
		
		try {
			PreparedStatement ps=connection.prepareStatement("select max(TransactionId) from liabrarianRecord");
			ResultSet rs=ps.executeQuery();
			rs.next();
			System.out.println("Transaction Id: "+rs.getInt(1));
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			
		}
		
	}
	
	
	
	public static void returnBook() {
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter Transaction ID: ");
		int trans_id=sc.nextInt();
		
		if(!transactionIdverification(trans_id)){
			System.out.println("Transaction Id Not Found!!!");
			return;
		}
		
		System.out.println("Enter Book ID: ");
		int book_id=sc.nextInt();
		
		if(!bookVerification(trans_id, book_id)) {
			System.out.println("Not issued the same book!!!");
			return;
		}
		

		try {
			PreparedStatement ps=connection.prepareStatement("update librarianRecord"
					+ " set ReturnDate=curdate(),Status='Returned',Penalty=? where TransactionId=?");
			
			ps.setInt(1, penaltyCalculation(trans_id));
			ps.setInt(2,trans_id);
			
			ps.executeUpdate();
			
			System.out.println("Book returned Successfully! ");
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		//update book count
		try {
			PreparedStatement ps=connection.prepareStatement("update librarianRecord set Quantity=Quantity+1 where BookId=?");
			ps.setInt(1,book_id );
			ps.executeUpdate();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		
		//fetching studentId from transcationId
		
		int student_id;
		
		try {
			PreparedStatement ps=connection.prepareStatement("select student_Id from libraianRecord where TransactionId=?");
			ps.setInt(1,trans_id );
			ResultSet rs=ps.executeQuery();
			rs.next();
			student_id=rs.getInt(1);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		
		//update in studentdetails
		
		try {
			PreparedStatement ps=connection.prepareStatement("update studentdetails set TotalIssued=TotalIssued-1 where student_Id=?");
			ps.setInt(1,student_id );
			ps.executeUpdate();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		
	}
	
//---------------------------------------------------------------------------------------------------------------------------
	
//-----------------------------Printing the Details---------------------------------------------------------------------------------
	
	public static void printStudentDetails() {
		try {
			PreparedStatement ps=connection.prepareStatement("select * from studentdetails ");
			ResultSet rs=ps.executeQuery();
			ResultSetMetaData rsmd=rs.getMetaData();
			int columnNo=rsmd.getColumnCount();
			
			for(int i=1;i<=columnNo;i++) {
				System.out.print(rsmd.getColumnName(i)+"   ");
			}
			System.out.println();
			
			while(rs.next()) {
				for(int i=1;i<=columnNo;i++) {
					System.out.print(rs.getString(i)+"   ");
				}
				System.out.println();
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void printLibraryBookDetails() {
		try {
			PreparedStatement ps=connection.prepareStatement("select * from libraryBooks ");
			ResultSet rs=ps.executeQuery();
			ResultSetMetaData rsmd=rs.getMetaData();
			int columnNo=rsmd.getColumnCount();
			
			for(int i=1;i<columnNo;i++) {
				System.out.print(rsmd.getColumnName(i)+"   ");
			}
			System.out.println();
			
			while(rs.next()) {
				for(int i=1;i<=columnNo;i++) {
					System.out.print(rs.getString(i)+"   ");
				}
				System.out.println();
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void printLibrarianRecord() {
		try {
			PreparedStatement ps=connection.prepareStatement("select * from librarianRecord ");
			ResultSet rs=ps.executeQuery();
			ResultSetMetaData rsmd=rs.getMetaData();
			int columnNo=rsmd.getColumnCount();
			
			for(int i=1;i<=columnNo;i++) {
				System.out.print(rsmd.getColumnName(i)+"   ");
			}
			System.out.println();
			
			while(rs.next()) {
				for(int i=1;i<=columnNo;i++) {
					System.out.print(rs.getString(i)+"   ");
				}
				System.out.println();
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
//--------------------------------------------------------------------------------------------------------------------
	
//------------------------Verification Of Records-------------------------------------------------------------------------------------------
	
	public static boolean studentExists(int id) {
		try {
			PreparedStatement ps=connection.prepareStatement("select * from studentdetails where student_Id=? ");
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			
			return rs.next(); 
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}
	
	public static boolean bookExists(int id) {
		try {
			PreparedStatement ps=connection.prepareStatement("select * from libraryBooks where BookId=? ");
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1)>0;
			}
			return false;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}
	
	public static boolean studentLimit(int id) {
		try {
			PreparedStatement ps=connection.prepareStatement("select TotalIssued from studentdetails where student_Id=? ");
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			rs.next();
			return ((rs.getInt(1))==10);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}
	
	//to verify transaction id present in the records
	
	public static boolean transactionIdverification(int id) {
		try {
			PreparedStatement ps=connection.prepareStatement("select BookId from librarianRecord where TransactionId=? ");
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			
			return rs.next(); 
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}
	
	
	//to verify whether the student issued the same book or not
	
	public static boolean bookVerification(int t_id,int b_id) {
		try {
			PreparedStatement ps=connection.prepareStatement("select BookId from librarianRecord where TransactionId=? ");
			ps.setInt(1, t_id);
			ResultSet rs=ps.executeQuery();
			
			return rs.getInt(1)==b_id; 
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}
	
	public static int penaltyCalculation(int t_id) {
		int penalty=0;
		
		try {
			PreparedStatement ps=connection.prepareStatement("select dateDiff(curDate(),DateOfIssue)rs.get from librarianRecord where TransactionId=? ");
			ps.setInt(1, t_id);
			ResultSet rs=ps.executeQuery();
			rs.next();
			int days=rs.getInt(1);
			if(days>15) {
				penalty=days*2;
				return penalty;
			}
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			
		}
		return 0;
		
		
		
	}
	
	
	
}
