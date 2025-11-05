// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {
   private static final String DB_URL = "jdbc:sqlite:laundry.db";

   public DatabaseHelper() {
   }

   private static Connection connect() throws SQLException {
      Connection var0 = DriverManager.getConnection("jdbc:sqlite:laundry.db");
      if (var0 != null) {
         DatabaseMetaData var1 = var0.getMetaData();
         System.out.println("Connected to SQLite, driver version: " + var1.getDriverVersion());
      }

      return var0;
   }

   private static void createTables() {
      String var0 = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,email TEXT UNIQUE,phone TEXT)";
      String var1 = "CREATE TABLE IF NOT EXISTS orders (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,email TEXT,phone TEXT,pickup_date TEXT,delivery_date TEXT,location TEXT,total_amount INTEGER)";

      try {
         Connection var2 = connect();

         try {
            Statement var3 = var2.createStatement();

            try {
               var3.execute(var0);
               var3.execute(var1);
               System.out.println("✅ Tables created or already exist.");
            } catch (Throwable var8) {
               if (var3 != null) {
                  try {
                     var3.close();
                  } catch (Throwable var7) {
                     var8.addSuppressed(var7);
                  }
               }

               throw var8;
            }

            if (var3 != null) {
               var3.close();
            }
         } catch (Throwable var9) {
            if (var2 != null) {
               try {
                  var2.close();
               } catch (Throwable var6) {
                  var9.addSuppressed(var6);
               }
            }

            throw var9;
         }

         if (var2 != null) {
            var2.close();
         }
      } catch (SQLException var10) {
         System.out.println("❌ Error creating tables: " + var10.getMessage());
      }

   }

   public static void insertUser(String var0, String var1, String var2) {
      String var3 = "INSERT OR IGNORE INTO users(name, email, phone) VALUES(?, ?, ?)";

      try {
         Connection var4 = connect();

         try {
            PreparedStatement var5 = var4.prepareStatement(var3);

            try {
               var5.setString(1, var0);
               var5.setString(2, var1);
               var5.setString(3, var2);
               var5.executeUpdate();
               System.out.println("✅ User inserted or already exists: " + var0);
            } catch (Throwable var10) {
               if (var5 != null) {
                  try {
                     var5.close();
                  } catch (Throwable var9) {
                     var10.addSuppressed(var9);
                  }
               }

               throw var10;
            }

            if (var5 != null) {
               var5.close();
            }
         } catch (Throwable var11) {
            if (var4 != null) {
               try {
                  var4.close();
               } catch (Throwable var8) {
                  var11.addSuppressed(var8);
               }
            }

            throw var11;
         }

         if (var4 != null) {
            var4.close();
         }
      } catch (SQLException var12) {
         System.out.println("❌ Error inserting user: " + var12.getMessage());
      }

   }

   public static void insertOrder(String var0, String var1, String var2, String var3, String var4, String var5, int var6) {
      String var7 = "INSERT INTO orders(name, email, phone, pickup_date, delivery_date, location, total_amount) VALUES(?, ?, ?, ?, ?, ?, ?)";

      try {
         Connection var8 = connect();

         try {
            PreparedStatement var9 = var8.prepareStatement(var7);

            try {
               var9.setString(1, var0);
               var9.setString(2, var1);
               var9.setString(3, var2);
               var9.setString(4, var3);
               var9.setString(5, var4);
               var9.setString(6, var5);
               var9.setInt(7, var6);
               var9.executeUpdate();
               System.out.println("✅ Order saved successfully for " + var0);
            } catch (Throwable var14) {
               if (var9 != null) {
                  try {
                     var9.close();
                  } catch (Throwable var13) {
                     var14.addSuppressed(var13);
                  }
               }

               throw var14;
            }

            if (var9 != null) {
               var9.close();
            }
         } catch (Throwable var15) {
            if (var8 != null) {
               try {
                  var8.close();
               } catch (Throwable var12) {
                  var15.addSuppressed(var12);
               }
            }

            throw var15;
         }

         if (var8 != null) {
            var8.close();
         }
      } catch (SQLException var16) {
         System.out.println("❌ Error saving order: " + var16.getMessage());
      }

   }

   public static void showAllOrders() {
      String var0 = "SELECT * FROM orders";

      try {
         Connection var1 = connect();

         try {
            Statement var2 = var1.createStatement();

            try {
               ResultSet var3 = var2.executeQuery(var0);

               try {
                  System.out.println("\n\ud83d\udce6 All Orders:");

                  while(var3.next()) {
                     PrintStream var10000 = System.out;
                     int var10001 = var3.getInt("id");
                     var10000.println("" + var10001 + ". " + var3.getString("name") + " | " + var3.getString("email") + " | " + var3.getString("phone") + " | " + var3.getString("pickup_date") + " → " + var3.getString("delivery_date") + " | " + var3.getString("location") + " | ₹" + var3.getInt("total_amount"));
                  }
               } catch (Throwable var9) {
                  if (var3 != null) {
                     try {
                        var3.close();
                     } catch (Throwable var8) {
                        var9.addSuppressed(var8);
                     }
                  }

                  throw var9;
               }

               if (var3 != null) {
                  var3.close();
               }
            } catch (Throwable var10) {
               if (var2 != null) {
                  try {
                     var2.close();
                  } catch (Throwable var7) {
                     var10.addSuppressed(var7);
                  }
               }

               throw var10;
            }

            if (var2 != null) {
               var2.close();
            }
         } catch (Throwable var11) {
            if (var1 != null) {
               try {
                  var1.close();
               } catch (Throwable var6) {
                  var11.addSuppressed(var6);
               }
            }

            throw var11;
         }

         if (var1 != null) {
            var1.close();
         }
      } catch (SQLException var12) {
         System.out.println("❌ Error reading orders: " + var12.getMessage());
      }

   }

   static {
      try {
         createTables();
         System.out.println("✅ Database initialized and tables verified!");
      } catch (Exception var1) {
         System.out.println("❌ Database initialization failed: " + var1.getMessage());
      }

   }
}
