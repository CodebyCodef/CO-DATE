using Microsoft.AspNetCore.Mvc;
using Microsoft.Data.SqlClient;
using System.Security.Cryptography;
using System.Text;
using db_api.Models;

namespace db_api.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class LoginController : ControllerBase
    {
        private readonly string _connectionString = "Server=CODEF;Database=codef_deneme;User Id=sa;Password=Codef123;Encrypt=False;";

        [HttpPost]
        public IActionResult Login([FromBody] Login loginRequest)
        {
            if (string.IsNullOrEmpty(loginRequest.Email) || string.IsNullOrEmpty(loginRequest.Password))
            {
                return BadRequest("E-posta ve şifre gerekli.");
            }

            using (SqlConnection conn = new SqlConnection(_connectionString))
            {
                try
                {
                    conn.Open();
                    string query = "SELECT PasswordHash FROM kullanici_bilgileri WHERE Email = @Email";
                    using (SqlCommand cmd = new SqlCommand(query, conn))
                    {
                        cmd.Parameters.AddWithValue("@Email", loginRequest.Email);

                        var dbPasswordHash = cmd.ExecuteScalar()?.ToString();

                        if (dbPasswordHash == null)
                        {
                            return Unauthorized("Geçersiz e-posta veya şifre.");
                        }

                        // Şifre kontrolü (hash eşleştirme)
                        if (!VerifyPasswordHash(loginRequest.Password, dbPasswordHash))
                        {
                            return Unauthorized("Geçersiz e-posta veya şifre.");
                        }

                        return Ok("Giriş başarılı!");
                    }
                }
                catch (Exception ex)
                {
                    return StatusCode(500, $"Internal server error: {ex.Message}");
                }
            }
        }

        // Şifre Hash Kontrol Metodu
        private bool VerifyPasswordHash(string password, string storedHash)
        {
            using (var sha256 = SHA256.Create())
            {
                var computedHash = Convert.ToBase64String(sha256.ComputeHash(Encoding.UTF8.GetBytes(password)));
                return storedHash == computedHash;
            }
        }
    }
}
