namespace db_api.Models
{
    public class User
    {
        public int Id { get; set; }
        public string Ad { get; set; } = string.Empty;
        public string Soyad { get; set; } = string.Empty;
        public int Yas { get; set; }

    }
}
