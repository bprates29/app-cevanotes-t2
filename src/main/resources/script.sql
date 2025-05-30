CREATE TABLE rotulos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    estilo VARCHAR(50) NOT NULL,
    teor_alcoolico DOUBLE NOT NULL,
    cervejaria VARCHAR(100),
    data_cadastro DATE
);

INSERT INTO rotulos (nome, estilo, teor_alcoolico, cervejaria, data_cadastro) VALUES
 ('IPA Puro Malte', 'IPA', 6.7, 'Cervejaria Alpha', CURRENT_DATE),
 ('Weiss Tropical', 'Trigo', 5.2, 'Brauhaus', CURRENT_DATE),
 ('Stout Forte', 'Stout', 8.1, 'BlackRock', CURRENT_DATE),
 ('Pilsen Clássica', 'Pilsen', 4.5, 'Cervejaria Nacional', CURRENT_DATE),
 ('APA Citrus', 'APA', 5.8, 'Lúpulo Livre', CURRENT_DATE),
 ('Belgian Strong', 'Belgian Ale', 9.0, 'Bélgica Beer', CURRENT_DATE),
 ('Session IPA Light', 'Session IPA', 4.3, 'Hop Time', CURRENT_DATE),
 ('Porter Cremosa', 'Porter', 6.2, 'DarkSide Brew', CURRENT_DATE),
 ('Amber Ale Caramelada', 'Amber Ale', 5.5, 'Vale do Malte', CURRENT_DATE),
 ('Lager Refrescante', 'Lager', 4.7, 'Verão Beer Co.', CURRENT_DATE);