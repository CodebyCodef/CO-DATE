using Microsoft.AspNetCore.Mvc;
using Microsoft.Data.SqlClient;
using System;
using db_api.Models;

namespace db_api.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class RegisterController : ControllerBase
    {
        private readonly string _connectionString = "Server=CODEF;Database=codate_db;User Id=sa;Password=Codef123;Encrypt=False;";

        [HttpPost("Register")]
        public IActionResult Register([FromBody] Register model)
        {
            if (model == null)
            {
                return BadRequest("Kullanıcı bilgileri eksik!");
            }

            

            using (SqlConnection conn = new SqlConnection(_connectionString))
            {
                conn.Open();

                // 1. Email kontrolü: Aynı e-posta ile kayıt var mı?
                string emailCheckQuery = "SELECT COUNT(1) FROM kullanici_bilgileri WHERE Email = @Email";
                using (SqlCommand emailCmd = new SqlCommand(emailCheckQuery, conn))
                {
                    emailCmd.Parameters.AddWithValue("@Email", model.Email);
                    int emailExists = (int)emailCmd.ExecuteScalar();

                    if (emailExists > 0)
                    {
                        return BadRequest("Bu e-posta zaten kayıtlı!");
                    }
                }

                // 2. Kullanıcıyı kaydet
                string insertQuery = @"
                INSERT INTO kullanici_bilgileri (Email, Sifre, Isim, Soyisim, Yas, TelNo, Cinsiyet,Biyografi)
                VALUES (@Email, @Sifre, @Isim, @Soyisim, @Yas, @TelNo, @Cinsiyet, @Biyografi)";

                using (SqlCommand cmd = new SqlCommand(insertQuery, conn))
                {
                    cmd.Parameters.AddWithValue("@Email", model.Email);
                    cmd.Parameters.AddWithValue("@Sifre", model.Sifre); // Şifre hashlenmiş olmalı
                    cmd.Parameters.AddWithValue("@Isim", model.Isim);
                    cmd.Parameters.AddWithValue("@Soyisim", model.Soyisim);
                    cmd.Parameters.AddWithValue("@Yas", model.Yas);
                    cmd.Parameters.AddWithValue("@TelNo", model.TelNo);
                    cmd.Parameters.AddWithValue("@Cinsiyet", model.Cinsiyet);
                    cmd.Parameters.AddWithValue("@Biyografi", model.Biyografi ?? (object)DBNull.Value);



                    cmd.ExecuteNonQuery();
                }
            }

            return Ok("Kullanıcı başarıyla kaydedildi!");
        }
    }
}
