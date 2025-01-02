using Microsoft.AspNetCore.Mvc;
using Microsoft.Data.SqlClient; 
using System.Collections.Generic;
using db_api.Models; // User modelini kullanmak için eklenmeli

[ApiController]
[Route("api/[controller]")]
public class HomeController : ControllerBase
{
    private readonly string _connectionString = "Server=CODEF;Database=codef_deneme;User Id=sa;Password=Codef123;Encrypt=False;";

    [HttpGet]
    public IActionResult GetData()
    {
        var users = new List<User>();

        using (SqlConnection conn = new SqlConnection(_connectionString))
        {
            try
            {
                conn.Open();
                string query = "SELECT Id, ad, soyad, yas FROM dbo.kisi_bilgisi"; // Sütunları açıkça seçiyoruz
                SqlCommand cmd = new SqlCommand(query, conn);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    // User modeline veri ekliyoruz
                    users.Add(new User
                    {
                        Id = reader["Id"] != DBNull.Value ? Convert.ToInt32(reader["Id"]) : 0,
                        Ad = reader["ad"]?.ToString() ?? string.Empty,
                        Soyad = reader["soyad"]?.ToString() ?? string.Empty,
                        Yas = reader["yas"] != DBNull.Value ? Convert.ToInt32(reader["yas"]) : 0
                    });
                }

                return Ok(users); // JSON formatında User listesi döner
            }
            catch (SqlException sqlEx)
            {
                return StatusCode(500, $"Database error: {sqlEx.Message}");
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Internal server error: {ex.Message}");
            }
        }
    }
   
}
