# CrudProject

Java Swing tabanlı bu örnek uygulama, temel CRUD işlevlerini ve raporlama ekranlarını içerir. Projeyi GitHub üzerinde paylaşırken veritabanı bağlantı bilgilerini gizli tutmak için aşağıdaki adımları izleyin.

## Başlangıç

1. Depoyu klonlayın veya indirin.
2. JDK 17+ yüklü olduğundan emin olun.
3. Gerekli bağımlılıklar `lib` klasöründe bulunur; Eclipse/IDEA projesine ekleyin.

## Veritabanı Yapılandırması

Uygulama, bağlantı bilgilerini ortam değişkenlerinden veya proje kök dizinindeki `db.properties` dosyasından okur.

- Örnek dosya: `db.properties.example`
- Kişisel ayarlarınızı yapmak için bu dosyanın bir kopyasını alın:

```
cp db.properties.example db.properties
```

veya Windows PowerShell:

```
Copy-Item db.properties.example db.properties
```

`db.properties` dosyasında aşağıdaki anahtarları doldurun:

```
db.url=jdbc:sqlserver://sunucu:1433;databaseName=VeritabaniAdi;encrypt=true;trustServerCertificate=true
db.user=KullaniciAdi
db.password=Sifre
```

Alternatif olarak aşağıdaki ortam değişkenlerini kullanabilirsiniz:

- `DB_URL`
- `DB_USER`
- `DB_PASSWORD`

`db.properties` dosyası `.gitignore` içinde yer aldığı için repoya eklenmez. Bu sayede kişisel bağlantı bilgileriniz gizli kalır.

## Çalıştırma

1. Veritabanı bağlantı ayarlarınızı yapın.
2. IDE üzerinden `crudproject.CrudDesigner` sınıfını çalıştırın.
3. Tablo ve kullanıcı arayüzü üzerinden kayıtları yönetin.



