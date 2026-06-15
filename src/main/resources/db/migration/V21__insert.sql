
INSERT INTO categories (id, name) VALUES
  (UNHEX(REPLACE('a1000000-0000-0000-0000-000000000001','-','')), 'Electronics'),
  (UNHEX(REPLACE('a1000000-0000-0000-0000-000000000002','-','')), 'Clothing'),
  (UNHEX(REPLACE('a1000000-0000-0000-0000-000000000003','-','')), 'Home & Kitchen'),
  (UNHEX(REPLACE('a1000000-0000-0000-0000-000000000004','-','')), 'Sports & Outdoors'),
  (UNHEX(REPLACE('a1000000-0000-0000-0000-000000000005','-','')), 'Books'),
  (UNHEX(REPLACE('a1000000-0000-0000-0000-000000000006','-','')), 'Beauty & Personal Care'),
  (UNHEX(REPLACE('a1000000-0000-0000-0000-000000000007','-','')), 'Toys & Games'),
  (UNHEX(REPLACE('a1000000-0000-0000-0000-000000000008','-','')), 'Automotive'),
  (UNHEX(REPLACE('a1000000-0000-0000-0000-000000000009','-','')), 'Garden & Outdoors'),
  (UNHEX(REPLACE('a1000000-0000-0000-0000-000000000010','-','')), 'Office Supplies');


INSERT INTO products (id, name, thumb_nail, description, price, category_id) VALUES


(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000001','-','')),
 'Wireless Noise-Cancelling Headphones',
 'https://picsum.photos/seed/prod1/600/400',
 'Premium over-ear headphones with active noise cancellation and 30-hour battery life.',
 129.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000001','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000002','-','')),
 '4K Ultra HD Smart TV 55"',
 'https://picsum.photos/seed/prod2/600/400',
 'Crystal-clear 4K display with built-in streaming apps and voice control.',
 699.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000001','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000003','-','')),
 'Mechanical Gaming Keyboard',
 'https://picsum.photos/seed/prod3/600/400',
 'RGB backlit mechanical keyboard with tactile switches for precision gaming.',
 89.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000001','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000004','-','')),
 'Portable Bluetooth Speaker',
 'https://picsum.photos/seed/prod4/600/400',
 'Waterproof speaker with 360° sound and 20-hour playtime.',
 59.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000001','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000005','-','')),
 'Smartwatch Pro Series 5',
 'https://picsum.photos/seed/prod5/600/400',
 'Health tracking smartwatch with GPS, heart-rate monitor, and sleep analysis.',
 249.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000001','-',''))),


(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000006','-','')),
 'Classic Slim-Fit Chinos',
 'https://picsum.photos/seed/prod6/600/400',
 'Versatile cotton-blend chinos with a modern slim fit, available in multiple colours.',
 49.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000002','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000007','-','')),
 'Premium Merino Wool Sweater',
 'https://picsum.photos/seed/prod7/600/400',
 'Lightweight yet warm merino wool crew-neck sweater for all seasons.',
 79.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000002','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000008','-','')),
 'Running Sneakers Ultra Boost',
 'https://picsum.photos/seed/prod8/600/400',
 'High-performance running shoes with responsive foam cushioning.',
 119.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000002','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000009','-','')),
 'Women''s Floral Maxi Dress',
 'https://picsum.photos/seed/prod9/600/400',
 'Elegant floral-print maxi dress perfect for summer occasions.',
 64.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000002','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000010','-','')),
 'Insulated Winter Parka',
 'https://picsum.photos/seed/prod10/600/400',
 'Heavy-duty parka with 600-fill down insulation and waterproof shell.',
 199.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000002','-',''))),

-- ── Home & Kitchen (cat 03) ──────────────────────────────
(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000011','-','')),
 'Stainless Steel Cookware Set 10pc',
 'https://picsum.photos/seed/prod11/600/400',
 'Professional-grade stainless steel pots and pans with tri-ply construction.',
 159.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000003','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000012','-','')),
 'Espresso Machine Deluxe',
 'https://picsum.photos/seed/prod12/600/400',
 '15-bar pump espresso machine with integrated milk frother and programmable settings.',
 299.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000003','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000013','-','')),
 'Air Fryer XL 6Qt',
 'https://picsum.photos/seed/prod13/600/400',
 'Large-capacity air fryer with 8 cooking presets and dishwasher-safe basket.',
 89.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000003','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000014','-','')),
 'Bamboo Cutting Board Set',
 'https://picsum.photos/seed/prod14/600/400',
 'Set of 3 eco-friendly bamboo cutting boards with juice groove and non-slip feet.',
 34.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000003','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000015','-','')),
 'Robot Vacuum Cleaner Smart',
 'https://picsum.photos/seed/prod15/600/400',
 'Self-navigating robot vacuum with HEPA filter and auto-empty base station.',
 349.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000003','-',''))),

-- ── Sports & Outdoors (cat 04) ──────────────────────────
(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000016','-','')),
 'Adjustable Dumbbell Set 5-50lb',
 'https://picsum.photos/seed/prod16/600/400',
 'Space-saving adjustable dumbbells that replace 15 sets in one compact unit.',
 299.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000004','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000017','-','')),
 'Yoga Mat Premium Non-Slip',
 'https://picsum.photos/seed/prod17/600/400',
 '6mm thick eco-friendly TPE yoga mat with alignment lines and carrying strap.',
 39.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000004','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000018','-','')),
 'Mountain Bike 29" Hardtail',
 'https://picsum.photos/seed/prod18/600/400',
 'Aluminum hardtail mountain bike with 21-speed Shimano gears and hydraulic brakes.',
 649.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000004','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000019','-','')),
 '4-Person Camping Tent',
 'https://picsum.photos/seed/prod19/600/400',
 'Weatherproof dome tent with quick setup poles and two vestibule doors.',
 129.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000004','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000020','-','')),
 'Insulated Hiking Water Bottle 32oz',
 'https://picsum.photos/seed/prod20/600/400',
 'Triple-wall vacuum insulated stainless steel bottle keeps drinks cold 48hrs.',
 34.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000004','-',''))),

-- ── Books (cat 05) ─────────────────────────────────────
(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000021','-','')),
 'Atomic Habits – James Clear',
 'https://picsum.photos/seed/prod21/600/400',
 'A proven framework for building good habits and breaking bad ones.',
 16.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000005','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000022','-','')),
 'Deep Work – Cal Newport',
 'https://picsum.photos/seed/prod22/600/400',
 'Rules for focused success in a distracted world.',
 15.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000005','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000023','-','')),
 'The Psychology of Money',
 'https://picsum.photos/seed/prod23/600/400',
 'Timeless lessons on wealth, greed, and happiness by Morgan Housel.',
 17.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000005','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000024','-','')),
 'Clean Code – Robert C. Martin',
 'https://picsum.photos/seed/prod24/600/400',
 'A handbook of agile software craftsmanship for professional developers.',
 39.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000005','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000025','-','')),
 'Sapiens – Yuval Noah Harari',
 'https://picsum.photos/seed/prod25/600/400',
 'A brief history of humankind from the Stone Age to the present.',
 18.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000005','-',''))),

-- ── Beauty & Personal Care (cat 06) ──────────────────────
(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000026','-','')),
 'Vitamin C Brightening Serum 30ml',
 'https://picsum.photos/seed/prod26/600/400',
 '20% stabilised vitamin C serum that fades dark spots and boosts radiance.',
 42.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000006','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000027','-','')),
 'Professional Hair Dryer 2200W',
 'https://picsum.photos/seed/prod27/600/400',
 'Ionic tourmaline hair dryer with 3 heat settings and cool-shot button.',
 74.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000006','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000028','-','')),
 'Electric Facial Cleansing Brush',
 'https://picsum.photos/seed/prod28/600/400',
 'Silicone sonic facial brush with 3 speeds and waterproof design.',
 54.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000006','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000029','-','')),
 'Retinol Night Cream 50ml',
 'https://picsum.photos/seed/prod29/600/400',
 'Anti-aging retinol night cream with hyaluronic acid and niacinamide.',
 36.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000006','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000030','-','')),
 'Luxury Perfume Eau de Parfum 100ml',
 'https://picsum.photos/seed/prod30/600/400',
 'Long-lasting floral-woody fragrance with bergamot, rose, and sandalwood notes.',
 89.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000006','-',''))),

-- ── Toys & Games (cat 07) ─────────────────────────────
(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000031','-','')),
 'LEGO City Police Station 743pcs',
 'https://picsum.photos/seed/prod31/600/400',
 'Detailed LEGO city police station set with jail, garage, and 6 minifigures.',
 79.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000007','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000032','-','')),
 'Remote Control Racing Car',
 'https://picsum.photos/seed/prod32/600/400',
 '1:10 scale RC car with 30km/h top speed and 60-min play time per charge.',
 64.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000007','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000033','-','')),
 'Strategy Board Game – Catan',
 'https://picsum.photos/seed/prod33/600/400',
 'Classic resource-trading strategy game for 3–4 players, ages 10+.',
 44.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000007','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000034','-','')),
 'Interactive Learning Robot Toy',
 'https://picsum.photos/seed/prod34/600/400',
 'Programmable STEM robot that teaches coding basics through play for ages 6+.',
 59.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000007','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000035','-','')),
 '1000-Piece Landscape Jigsaw Puzzle',
 'https://picsum.photos/seed/prod35/600/400',
 'High-quality 1000-piece jigsaw puzzle featuring a stunning mountain sunrise.',
 24.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000007','-',''))),

-- ── Automotive (cat 08) ───────────────────────────────
(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000036','-','')),
 'Dash Cam 4K with GPS',
 'https://picsum.photos/seed/prod36/600/400',
 'Front and rear 4K dash camera with night vision, GPS logging, and Wi-Fi.',
 149.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000008','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000037','-','')),
 'Portable Jump Starter 2000A',
 'https://picsum.photos/seed/prod37/600/400',
 'Compact lithium jump starter with USB-C power bank and LED torch.',
 89.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000008','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000038','-','')),
 'Universal Car Phone Mount',
 'https://picsum.photos/seed/prod38/600/400',
 'Magnetic air-vent phone mount compatible with all smartphones up to 7".',
 19.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000008','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000039','-','')),
 'Car Vacuum Cleaner Cordless',
 'https://picsum.photos/seed/prod39/600/400',
 'Handheld cordless car vacuum with HEPA filter and 25-min runtime.',
 54.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000008','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000040','-','')),
 'OBD2 Bluetooth Car Diagnostic Scanner',
 'https://picsum.photos/seed/prod40/600/400',
 'Wireless OBD2 scanner reads and clears engine codes via smartphone app.',
 34.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000008','-',''))),

-- ── Garden & Outdoors (cat 09) ────────────────────────
(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000041','-','')),
 'Solar Garden Lights Set of 12',
 'https://picsum.photos/seed/prod41/600/400',
 'Stainless steel solar-powered pathway lights with warm white LEDs.',
 44.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000009','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000042','-','')),
 'Raised Garden Bed Cedar Wood',
 'https://picsum.photos/seed/prod42/600/400',
 '4×4ft cedar raised planting bed, naturally rot-resistant, easy assembly.',
 79.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000009','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000043','-','')),
 'Cordless Electric Lawn Mower',
 'https://picsum.photos/seed/prod43/600/400',
 '40V cordless lawn mower with 40cm cutting width and 50L grass catcher.',
 299.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000009','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000044','-','')),
 'Outdoor Hanging Egg Chair',
 'https://picsum.photos/seed/prod44/600/400',
 'Wicker rattan hanging egg chair with cushion, UV-resistant for all-weather use.',
 189.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000009','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000045','-','')),
 'Garden Tool Set 10pc',
 'https://picsum.photos/seed/prod45/600/400',
 'Ergonomic 10-piece garden tool set with trowel, pruner, rake, and carry bag.',
 49.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000009','-',''))),

-- ── Office Supplies (cat 10) ─────────────────────────
(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000046','-','')),
 'Ergonomic Mesh Office Chair',
 'https://picsum.photos/seed/prod46/600/400',
 'Lumbar-support mesh chair with adjustable armrests, headrest, and tilt lock.',
 229.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000010','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000047','-','')),
 'Standing Desk Converter',
 'https://picsum.photos/seed/prod47/600/400',
 'Height-adjustable sit-stand desk converter with dual monitor shelf and keyboard tray.',
 179.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000010','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000048','-','')),
 'Wireless Charging Desk Organiser',
 'https://picsum.photos/seed/prod48/600/400',
 'Multi-compartment desk organiser with built-in 15W Qi wireless charger.',
 54.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000010','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000049','-','')),
 'Laser Printer All-in-One',
 'https://picsum.photos/seed/prod49/600/400',
 'Monochrome laser printer with scan, copy, and Wi-Fi printing up to 34ppm.',
 249.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000010','-',''))),

(UNHEX(REPLACE('b1000000-0000-0000-0000-000000000050','-','')),
 'Leather Notebook & Pen Gift Set',
 'https://picsum.photos/seed/prod50/600/400',
 'Genuine leather A5 journal with premium ballpoint pen in gift box.',
 29.99,
 UNHEX(REPLACE('a1000000-0000-0000-0000-000000000010','-','')));



INSERT INTO product_images (id, image_url, image_hash, resource_type, public_id, product_id) VALUES

-- Product 1
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000001','-','')),'https://picsum.photos/seed/prod1_img1/800/600',SHA2('https://picsum.photos/seed/prod1_img1/800/600',256),'image','products/prod1/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000001','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000002','-','')),'https://picsum.photos/seed/prod1_img2/800/600',SHA2('https://picsum.photos/seed/prod1_img2/800/600',256),'image','products/prod1/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000001','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000003','-','')),'https://picsum.photos/seed/prod1_img3/800/600',SHA2('https://picsum.photos/seed/prod1_img3/800/600',256),'image','products/prod1/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000001','-',''))),
-- Product 2
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000004','-','')),'https://picsum.photos/seed/prod2_img1/800/600',SHA2('https://picsum.photos/seed/prod2_img1/800/600',256),'image','products/prod2/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000002','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000005','-','')),'https://picsum.photos/seed/prod2_img2/800/600',SHA2('https://picsum.photos/seed/prod2_img2/800/600',256),'image','products/prod2/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000002','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000006','-','')),'https://picsum.photos/seed/prod2_img3/800/600',SHA2('https://picsum.photos/seed/prod2_img3/800/600',256),'image','products/prod2/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000002','-',''))),
-- Product 3
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000007','-','')),'https://picsum.photos/seed/prod3_img1/800/600',SHA2('https://picsum.photos/seed/prod3_img1/800/600',256),'image','products/prod3/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000003','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000008','-','')),'https://picsum.photos/seed/prod3_img2/800/600',SHA2('https://picsum.photos/seed/prod3_img2/800/600',256),'image','products/prod3/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000003','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000009','-','')),'https://picsum.photos/seed/prod3_img3/800/600',SHA2('https://picsum.photos/seed/prod3_img3/800/600',256),'image','products/prod3/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000003','-',''))),
-- Product 4
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000010','-','')),'https://picsum.photos/seed/prod4_img1/800/600',SHA2('https://picsum.photos/seed/prod4_img1/800/600',256),'image','products/prod4/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000004','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000011','-','')),'https://picsum.photos/seed/prod4_img2/800/600',SHA2('https://picsum.photos/seed/prod4_img2/800/600',256),'image','products/prod4/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000004','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000012','-','')),'https://picsum.photos/seed/prod4_img3/800/600',SHA2('https://picsum.photos/seed/prod4_img3/800/600',256),'image','products/prod4/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000004','-',''))),
-- Product 5
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000013','-','')),'https://picsum.photos/seed/prod5_img1/800/600',SHA2('https://picsum.photos/seed/prod5_img1/800/600',256),'image','products/prod5/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000005','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000014','-','')),'https://picsum.photos/seed/prod5_img2/800/600',SHA2('https://picsum.photos/seed/prod5_img2/800/600',256),'image','products/prod5/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000005','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000015','-','')),'https://picsum.photos/seed/prod5_img3/800/600',SHA2('https://picsum.photos/seed/prod5_img3/800/600',256),'image','products/prod5/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000005','-',''))),
-- Product 6
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000016','-','')),'https://picsum.photos/seed/prod6_img1/800/600',SHA2('https://picsum.photos/seed/prod6_img1/800/600',256),'image','products/prod6/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000006','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000017','-','')),'https://picsum.photos/seed/prod6_img2/800/600',SHA2('https://picsum.photos/seed/prod6_img2/800/600',256),'image','products/prod6/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000006','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000018','-','')),'https://picsum.photos/seed/prod6_img3/800/600',SHA2('https://picsum.photos/seed/prod6_img3/800/600',256),'image','products/prod6/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000006','-',''))),
-- Product 7
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000019','-','')),'https://picsum.photos/seed/prod7_img1/800/600',SHA2('https://picsum.photos/seed/prod7_img1/800/600',256),'image','products/prod7/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000007','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000020','-','')),'https://picsum.photos/seed/prod7_img2/800/600',SHA2('https://picsum.photos/seed/prod7_img2/800/600',256),'image','products/prod7/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000007','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000021','-','')),'https://picsum.photos/seed/prod7_img3/800/600',SHA2('https://picsum.photos/seed/prod7_img3/800/600',256),'image','products/prod7/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000007','-',''))),
-- Product 8
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000022','-','')),'https://picsum.photos/seed/prod8_img1/800/600',SHA2('https://picsum.photos/seed/prod8_img1/800/600',256),'image','products/prod8/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000008','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000023','-','')),'https://picsum.photos/seed/prod8_img2/800/600',SHA2('https://picsum.photos/seed/prod8_img2/800/600',256),'image','products/prod8/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000008','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000024','-','')),'https://picsum.photos/seed/prod8_img3/800/600',SHA2('https://picsum.photos/seed/prod8_img3/800/600',256),'image','products/prod8/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000008','-',''))),
-- Product 9
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000025','-','')),'https://picsum.photos/seed/prod9_img1/800/600',SHA2('https://picsum.photos/seed/prod9_img1/800/600',256),'image','products/prod9/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000009','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000026','-','')),'https://picsum.photos/seed/prod9_img2/800/600',SHA2('https://picsum.photos/seed/prod9_img2/800/600',256),'image','products/prod9/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000009','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000027','-','')),'https://picsum.photos/seed/prod9_img3/800/600',SHA2('https://picsum.photos/seed/prod9_img3/800/600',256),'image','products/prod9/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000009','-',''))),
-- Product 10
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000028','-','')),'https://picsum.photos/seed/prod10_img1/800/600',SHA2('https://picsum.photos/seed/prod10_img1/800/600',256),'image','products/prod10/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000010','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000029','-','')),'https://picsum.photos/seed/prod10_img2/800/600',SHA2('https://picsum.photos/seed/prod10_img2/800/600',256),'image','products/prod10/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000010','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000030','-','')),'https://picsum.photos/seed/prod10_img3/800/600',SHA2('https://picsum.photos/seed/prod10_img3/800/600',256),'image','products/prod10/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000010','-',''))),
-- Product 11
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000031','-','')),'https://picsum.photos/seed/prod11_img1/800/600',SHA2('https://picsum.photos/seed/prod11_img1/800/600',256),'image','products/prod11/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000011','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000032','-','')),'https://picsum.photos/seed/prod11_img2/800/600',SHA2('https://picsum.photos/seed/prod11_img2/800/600',256),'image','products/prod11/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000011','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000033','-','')),'https://picsum.photos/seed/prod11_img3/800/600',SHA2('https://picsum.photos/seed/prod11_img3/800/600',256),'image','products/prod11/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000011','-',''))),
-- Product 12
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000034','-','')),'https://picsum.photos/seed/prod12_img1/800/600',SHA2('https://picsum.photos/seed/prod12_img1/800/600',256),'image','products/prod12/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000012','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000035','-','')),'https://picsum.photos/seed/prod12_img2/800/600',SHA2('https://picsum.photos/seed/prod12_img2/800/600',256),'image','products/prod12/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000012','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000036','-','')),'https://picsum.photos/seed/prod12_img3/800/600',SHA2('https://picsum.photos/seed/prod12_img3/800/600',256),'image','products/prod12/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000012','-',''))),
-- Product 13
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000037','-','')),'https://picsum.photos/seed/prod13_img1/800/600',SHA2('https://picsum.photos/seed/prod13_img1/800/600',256),'image','products/prod13/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000013','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000038','-','')),'https://picsum.photos/seed/prod13_img2/800/600',SHA2('https://picsum.photos/seed/prod13_img2/800/600',256),'image','products/prod13/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000013','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000039','-','')),'https://picsum.photos/seed/prod13_img3/800/600',SHA2('https://picsum.photos/seed/prod13_img3/800/600',256),'image','products/prod13/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000013','-',''))),
-- Product 14
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000040','-','')),'https://picsum.photos/seed/prod14_img1/800/600',SHA2('https://picsum.photos/seed/prod14_img1/800/600',256),'image','products/prod14/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000014','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000041','-','')),'https://picsum.photos/seed/prod14_img2/800/600',SHA2('https://picsum.photos/seed/prod14_img2/800/600',256),'image','products/prod14/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000014','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000042','-','')),'https://picsum.photos/seed/prod14_img3/800/600',SHA2('https://picsum.photos/seed/prod14_img3/800/600',256),'image','products/prod14/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000014','-',''))),
-- Product 15
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000043','-','')),'https://picsum.photos/seed/prod15_img1/800/600',SHA2('https://picsum.photos/seed/prod15_img1/800/600',256),'image','products/prod15/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000015','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000044','-','')),'https://picsum.photos/seed/prod15_img2/800/600',SHA2('https://picsum.photos/seed/prod15_img2/800/600',256),'image','products/prod15/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000015','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000045','-','')),'https://picsum.photos/seed/prod15_img3/800/600',SHA2('https://picsum.photos/seed/prod15_img3/800/600',256),'image','products/prod15/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000015','-',''))),
-- Product 16
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000046','-','')),'https://picsum.photos/seed/prod16_img1/800/600',SHA2('https://picsum.photos/seed/prod16_img1/800/600',256),'image','products/prod16/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000016','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000047','-','')),'https://picsum.photos/seed/prod16_img2/800/600',SHA2('https://picsum.photos/seed/prod16_img2/800/600',256),'image','products/prod16/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000016','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000048','-','')),'https://picsum.photos/seed/prod16_img3/800/600',SHA2('https://picsum.photos/seed/prod16_img3/800/600',256),'image','products/prod16/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000016','-',''))),
-- Product 17
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000049','-','')),'https://picsum.photos/seed/prod17_img1/800/600',SHA2('https://picsum.photos/seed/prod17_img1/800/600',256),'image','products/prod17/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000017','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000050','-','')),'https://picsum.photos/seed/prod17_img2/800/600',SHA2('https://picsum.photos/seed/prod17_img2/800/600',256),'image','products/prod17/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000017','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000051','-','')),'https://picsum.photos/seed/prod17_img3/800/600',SHA2('https://picsum.photos/seed/prod17_img3/800/600',256),'image','products/prod17/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000017','-',''))),
-- Product 18
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000052','-','')),'https://picsum.photos/seed/prod18_img1/800/600',SHA2('https://picsum.photos/seed/prod18_img1/800/600',256),'image','products/prod18/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000018','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000053','-','')),'https://picsum.photos/seed/prod18_img2/800/600',SHA2('https://picsum.photos/seed/prod18_img2/800/600',256),'image','products/prod18/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000018','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000054','-','')),'https://picsum.photos/seed/prod18_img3/800/600',SHA2('https://picsum.photos/seed/prod18_img3/800/600',256),'image','products/prod18/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000018','-',''))),
-- Product 19
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000055','-','')),'https://picsum.photos/seed/prod19_img1/800/600',SHA2('https://picsum.photos/seed/prod19_img1/800/600',256),'image','products/prod19/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000019','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000056','-','')),'https://picsum.photos/seed/prod19_img2/800/600',SHA2('https://picsum.photos/seed/prod19_img2/800/600',256),'image','products/prod19/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000019','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000057','-','')),'https://picsum.photos/seed/prod19_img3/800/600',SHA2('https://picsum.photos/seed/prod19_img3/800/600',256),'image','products/prod19/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000019','-',''))),
-- Product 20
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000058','-','')),'https://picsum.photos/seed/prod20_img1/800/600',SHA2('https://picsum.photos/seed/prod20_img1/800/600',256),'image','products/prod20/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000020','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000059','-','')),'https://picsum.photos/seed/prod20_img2/800/600',SHA2('https://picsum.photos/seed/prod20_img2/800/600',256),'image','products/prod20/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000020','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000060','-','')),'https://picsum.photos/seed/prod20_img3/800/600',SHA2('https://picsum.photos/seed/prod20_img3/800/600',256),'image','products/prod20/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000020','-',''))),
-- Product 21
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000061','-','')),'https://picsum.photos/seed/prod21_img1/800/600',SHA2('https://picsum.photos/seed/prod21_img1/800/600',256),'image','products/prod21/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000021','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000062','-','')),'https://picsum.photos/seed/prod21_img2/800/600',SHA2('https://picsum.photos/seed/prod21_img2/800/600',256),'image','products/prod21/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000021','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000063','-','')),'https://picsum.photos/seed/prod21_img3/800/600',SHA2('https://picsum.photos/seed/prod21_img3/800/600',256),'image','products/prod21/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000021','-',''))),
-- Product 22
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000064','-','')),'https://picsum.photos/seed/prod22_img1/800/600',SHA2('https://picsum.photos/seed/prod22_img1/800/600',256),'image','products/prod22/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000022','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000065','-','')),'https://picsum.photos/seed/prod22_img2/800/600',SHA2('https://picsum.photos/seed/prod22_img2/800/600',256),'image','products/prod22/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000022','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000066','-','')),'https://picsum.photos/seed/prod22_img3/800/600',SHA2('https://picsum.photos/seed/prod22_img3/800/600',256),'image','products/prod22/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000022','-',''))),
-- Product 23
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000067','-','')),'https://picsum.photos/seed/prod23_img1/800/600',SHA2('https://picsum.photos/seed/prod23_img1/800/600',256),'image','products/prod23/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000023','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000068','-','')),'https://picsum.photos/seed/prod23_img2/800/600',SHA2('https://picsum.photos/seed/prod23_img2/800/600',256),'image','products/prod23/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000023','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000069','-','')),'https://picsum.photos/seed/prod23_img3/800/600',SHA2('https://picsum.photos/seed/prod23_img3/800/600',256),'image','products/prod23/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000023','-',''))),
-- Product 24
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000070','-','')),'https://picsum.photos/seed/prod24_img1/800/600',SHA2('https://picsum.photos/seed/prod24_img1/800/600',256),'image','products/prod24/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000024','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000071','-','')),'https://picsum.photos/seed/prod24_img2/800/600',SHA2('https://picsum.photos/seed/prod24_img2/800/600',256),'image','products/prod24/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000024','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000072','-','')),'https://picsum.photos/seed/prod24_img3/800/600',SHA2('https://picsum.photos/seed/prod24_img3/800/600',256),'image','products/prod24/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000024','-',''))),
-- Product 25
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000073','-','')),'https://picsum.photos/seed/prod25_img1/800/600',SHA2('https://picsum.photos/seed/prod25_img1/800/600',256),'image','products/prod25/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000025','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000074','-','')),'https://picsum.photos/seed/prod25_img2/800/600',SHA2('https://picsum.photos/seed/prod25_img2/800/600',256),'image','products/prod25/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000025','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000075','-','')),'https://picsum.photos/seed/prod25_img3/800/600',SHA2('https://picsum.photos/seed/prod25_img3/800/600',256),'image','products/prod25/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000025','-',''))),
-- Product 26
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000076','-','')),'https://picsum.photos/seed/prod26_img1/800/600',SHA2('https://picsum.photos/seed/prod26_img1/800/600',256),'image','products/prod26/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000026','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000077','-','')),'https://picsum.photos/seed/prod26_img2/800/600',SHA2('https://picsum.photos/seed/prod26_img2/800/600',256),'image','products/prod26/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000026','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000078','-','')),'https://picsum.photos/seed/prod26_img3/800/600',SHA2('https://picsum.photos/seed/prod26_img3/800/600',256),'image','products/prod26/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000026','-',''))),
-- Product 27
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000079','-','')),'https://picsum.photos/seed/prod27_img1/800/600',SHA2('https://picsum.photos/seed/prod27_img1/800/600',256),'image','products/prod27/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000027','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000080','-','')),'https://picsum.photos/seed/prod27_img2/800/600',SHA2('https://picsum.photos/seed/prod27_img2/800/600',256),'image','products/prod27/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000027','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000081','-','')),'https://picsum.photos/seed/prod27_img3/800/600',SHA2('https://picsum.photos/seed/prod27_img3/800/600',256),'image','products/prod27/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000027','-',''))),
-- Product 28
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000082','-','')),'https://picsum.photos/seed/prod28_img1/800/600',SHA2('https://picsum.photos/seed/prod28_img1/800/600',256),'image','products/prod28/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000028','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000083','-','')),'https://picsum.photos/seed/prod28_img2/800/600',SHA2('https://picsum.photos/seed/prod28_img2/800/600',256),'image','products/prod28/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000028','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000084','-','')),'https://picsum.photos/seed/prod28_img3/800/600',SHA2('https://picsum.photos/seed/prod28_img3/800/600',256),'image','products/prod28/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000028','-',''))),
-- Product 29
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000085','-','')),'https://picsum.photos/seed/prod29_img1/800/600',SHA2('https://picsum.photos/seed/prod29_img1/800/600',256),'image','products/prod29/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000029','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000086','-','')),'https://picsum.photos/seed/prod29_img2/800/600',SHA2('https://picsum.photos/seed/prod29_img2/800/600',256),'image','products/prod29/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000029','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000087','-','')),'https://picsum.photos/seed/prod29_img3/800/600',SHA2('https://picsum.photos/seed/prod29_img3/800/600',256),'image','products/prod29/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000029','-',''))),
-- Product 30
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000088','-','')),'https://picsum.photos/seed/prod30_img1/800/600',SHA2('https://picsum.photos/seed/prod30_img1/800/600',256),'image','products/prod30/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000030','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000089','-','')),'https://picsum.photos/seed/prod30_img2/800/600',SHA2('https://picsum.photos/seed/prod30_img2/800/600',256),'image','products/prod30/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000030','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000090','-','')),'https://picsum.photos/seed/prod30_img3/800/600',SHA2('https://picsum.photos/seed/prod30_img3/800/600',256),'image','products/prod30/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000030','-',''))),
-- Product 31
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000091','-','')),'https://picsum.photos/seed/prod31_img1/800/600',SHA2('https://picsum.photos/seed/prod31_img1/800/600',256),'image','products/prod31/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000031','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000092','-','')),'https://picsum.photos/seed/prod31_img2/800/600',SHA2('https://picsum.photos/seed/prod31_img2/800/600',256),'image','products/prod31/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000031','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000093','-','')),'https://picsum.photos/seed/prod31_img3/800/600',SHA2('https://picsum.photos/seed/prod31_img3/800/600',256),'image','products/prod31/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000031','-',''))),
-- Product 32
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000094','-','')),'https://picsum.photos/seed/prod32_img1/800/600',SHA2('https://picsum.photos/seed/prod32_img1/800/600',256),'image','products/prod32/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000032','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000095','-','')),'https://picsum.photos/seed/prod32_img2/800/600',SHA2('https://picsum.photos/seed/prod32_img2/800/600',256),'image','products/prod32/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000032','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000096','-','')),'https://picsum.photos/seed/prod32_img3/800/600',SHA2('https://picsum.photos/seed/prod32_img3/800/600',256),'image','products/prod32/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000032','-',''))),
-- Product 33
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000097','-','')),'https://picsum.photos/seed/prod33_img1/800/600',SHA2('https://picsum.photos/seed/prod33_img1/800/600',256),'image','products/prod33/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000033','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000098','-','')),'https://picsum.photos/seed/prod33_img2/800/600',SHA2('https://picsum.photos/seed/prod33_img2/800/600',256),'image','products/prod33/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000033','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000099','-','')),'https://picsum.photos/seed/prod33_img3/800/600',SHA2('https://picsum.photos/seed/prod33_img3/800/600',256),'image','products/prod33/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000033','-',''))),
-- Product 34
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000100','-','')),'https://picsum.photos/seed/prod34_img1/800/600',SHA2('https://picsum.photos/seed/prod34_img1/800/600',256),'image','products/prod34/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000034','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000101','-','')),'https://picsum.photos/seed/prod34_img2/800/600',SHA2('https://picsum.photos/seed/prod34_img2/800/600',256),'image','products/prod34/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000034','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000102','-','')),'https://picsum.photos/seed/prod34_img3/800/600',SHA2('https://picsum.photos/seed/prod34_img3/800/600',256),'image','products/prod34/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000034','-',''))),
-- Product 35
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000103','-','')),'https://picsum.photos/seed/prod35_img1/800/600',SHA2('https://picsum.photos/seed/prod35_img1/800/600',256),'image','products/prod35/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000035','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000104','-','')),'https://picsum.photos/seed/prod35_img2/800/600',SHA2('https://picsum.photos/seed/prod35_img2/800/600',256),'image','products/prod35/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000035','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000105','-','')),'https://picsum.photos/seed/prod35_img3/800/600',SHA2('https://picsum.photos/seed/prod35_img3/800/600',256),'image','products/prod35/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000035','-',''))),
-- Product 36
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000106','-','')),'https://picsum.photos/seed/prod36_img1/800/600',SHA2('https://picsum.photos/seed/prod36_img1/800/600',256),'image','products/prod36/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000036','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000107','-','')),'https://picsum.photos/seed/prod36_img2/800/600',SHA2('https://picsum.photos/seed/prod36_img2/800/600',256),'image','products/prod36/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000036','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000108','-','')),'https://picsum.photos/seed/prod36_img3/800/600',SHA2('https://picsum.photos/seed/prod36_img3/800/600',256),'image','products/prod36/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000036','-',''))),
-- Product 37
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000109','-','')),'https://picsum.photos/seed/prod37_img1/800/600',SHA2('https://picsum.photos/seed/prod37_img1/800/600',256),'image','products/prod37/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000037','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000110','-','')),'https://picsum.photos/seed/prod37_img2/800/600',SHA2('https://picsum.photos/seed/prod37_img2/800/600',256),'image','products/prod37/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000037','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000111','-','')),'https://picsum.photos/seed/prod37_img3/800/600',SHA2('https://picsum.photos/seed/prod37_img3/800/600',256),'image','products/prod37/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000037','-',''))),
-- Product 38
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000112','-','')),'https://picsum.photos/seed/prod38_img1/800/600',SHA2('https://picsum.photos/seed/prod38_img1/800/600',256),'image','products/prod38/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000038','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000113','-','')),'https://picsum.photos/seed/prod38_img2/800/600',SHA2('https://picsum.photos/seed/prod38_img2/800/600',256),'image','products/prod38/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000038','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000114','-','')),'https://picsum.photos/seed/prod38_img3/800/600',SHA2('https://picsum.photos/seed/prod38_img3/800/600',256),'image','products/prod38/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000038','-',''))),
-- Product 39
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000115','-','')),'https://picsum.photos/seed/prod39_img1/800/600',SHA2('https://picsum.photos/seed/prod39_img1/800/600',256),'image','products/prod39/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000039','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000116','-','')),'https://picsum.photos/seed/prod39_img2/800/600',SHA2('https://picsum.photos/seed/prod39_img2/800/600',256),'image','products/prod39/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000039','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000117','-','')),'https://picsum.photos/seed/prod39_img3/800/600',SHA2('https://picsum.photos/seed/prod39_img3/800/600',256),'image','products/prod39/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000039','-',''))),
-- Product 40
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000118','-','')),'https://picsum.photos/seed/prod40_img1/800/600',SHA2('https://picsum.photos/seed/prod40_img1/800/600',256),'image','products/prod40/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000040','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000119','-','')),'https://picsum.photos/seed/prod40_img2/800/600',SHA2('https://picsum.photos/seed/prod40_img2/800/600',256),'image','products/prod40/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000040','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000120','-','')),'https://picsum.photos/seed/prod40_img3/800/600',SHA2('https://picsum.photos/seed/prod40_img3/800/600',256),'image','products/prod40/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000040','-',''))),
-- Product 41
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000121','-','')),'https://picsum.photos/seed/prod41_img1/800/600',SHA2('https://picsum.photos/seed/prod41_img1/800/600',256),'image','products/prod41/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000041','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000122','-','')),'https://picsum.photos/seed/prod41_img2/800/600',SHA2('https://picsum.photos/seed/prod41_img2/800/600',256),'image','products/prod41/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000041','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000123','-','')),'https://picsum.photos/seed/prod41_img3/800/600',SHA2('https://picsum.photos/seed/prod41_img3/800/600',256),'image','products/prod41/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000041','-',''))),
-- Product 42
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000124','-','')),'https://picsum.photos/seed/prod42_img1/800/600',SHA2('https://picsum.photos/seed/prod42_img1/800/600',256),'image','products/prod42/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000042','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000125','-','')),'https://picsum.photos/seed/prod42_img2/800/600',SHA2('https://picsum.photos/seed/prod42_img2/800/600',256),'image','products/prod42/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000042','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000126','-','')),'https://picsum.photos/seed/prod42_img3/800/600',SHA2('https://picsum.photos/seed/prod42_img3/800/600',256),'image','products/prod42/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000042','-',''))),
-- Product 43
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000127','-','')),'https://picsum.photos/seed/prod43_img1/800/600',SHA2('https://picsum.photos/seed/prod43_img1/800/600',256),'image','products/prod43/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000043','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000128','-','')),'https://picsum.photos/seed/prod43_img2/800/600',SHA2('https://picsum.photos/seed/prod43_img2/800/600',256),'image','products/prod43/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000043','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000129','-','')),'https://picsum.photos/seed/prod43_img3/800/600',SHA2('https://picsum.photos/seed/prod43_img3/800/600',256),'image','products/prod43/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000043','-',''))),
-- Product 44
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000130','-','')),'https://picsum.photos/seed/prod44_img1/800/600',SHA2('https://picsum.photos/seed/prod44_img1/800/600',256),'image','products/prod44/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000044','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000131','-','')),'https://picsum.photos/seed/prod44_img2/800/600',SHA2('https://picsum.photos/seed/prod44_img2/800/600',256),'image','products/prod44/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000044','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000132','-','')),'https://picsum.photos/seed/prod44_img3/800/600',SHA2('https://picsum.photos/seed/prod44_img3/800/600',256),'image','products/prod44/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000044','-',''))),
-- Product 45
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000133','-','')),'https://picsum.photos/seed/prod45_img1/800/600',SHA2('https://picsum.photos/seed/prod45_img1/800/600',256),'image','products/prod45/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000045','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000134','-','')),'https://picsum.photos/seed/prod45_img2/800/600',SHA2('https://picsum.photos/seed/prod45_img2/800/600',256),'image','products/prod45/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000045','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000135','-','')),'https://picsum.photos/seed/prod45_img3/800/600',SHA2('https://picsum.photos/seed/prod45_img3/800/600',256),'image','products/prod45/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000045','-',''))),
-- Product 46
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000136','-','')),'https://picsum.photos/seed/prod46_img1/800/600',SHA2('https://picsum.photos/seed/prod46_img1/800/600',256),'image','products/prod46/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000046','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000137','-','')),'https://picsum.photos/seed/prod46_img2/800/600',SHA2('https://picsum.photos/seed/prod46_img2/800/600',256),'image','products/prod46/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000046','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000138','-','')),'https://picsum.photos/seed/prod46_img3/800/600',SHA2('https://picsum.photos/seed/prod46_img3/800/600',256),'image','products/prod46/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000046','-',''))),
-- Product 47
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000139','-','')),'https://picsum.photos/seed/prod47_img1/800/600',SHA2('https://picsum.photos/seed/prod47_img1/800/600',256),'image','products/prod47/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000047','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000140','-','')),'https://picsum.photos/seed/prod47_img2/800/600',SHA2('https://picsum.photos/seed/prod47_img2/800/600',256),'image','products/prod47/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000047','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000141','-','')),'https://picsum.photos/seed/prod47_img3/800/600',SHA2('https://picsum.photos/seed/prod47_img3/800/600',256),'image','products/prod47/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000047','-',''))),
-- Product 48
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000142','-','')),'https://picsum.photos/seed/prod48_img1/800/600',SHA2('https://picsum.photos/seed/prod48_img1/800/600',256),'image','products/prod48/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000048','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000143','-','')),'https://picsum.photos/seed/prod48_img2/800/600',SHA2('https://picsum.photos/seed/prod48_img2/800/600',256),'image','products/prod48/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000048','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000144','-','')),'https://picsum.photos/seed/prod48_img3/800/600',SHA2('https://picsum.photos/seed/prod48_img3/800/600',256),'image','products/prod48/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000048','-',''))),
-- Product 49
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000145','-','')),'https://picsum.photos/seed/prod49_img1/800/600',SHA2('https://picsum.photos/seed/prod49_img1/800/600',256),'image','products/prod49/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000049','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000146','-','')),'https://picsum.photos/seed/prod49_img2/800/600',SHA2('https://picsum.photos/seed/prod49_img2/800/600',256),'image','products/prod49/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000049','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000147','-','')),'https://picsum.photos/seed/prod49_img3/800/600',SHA2('https://picsum.photos/seed/prod49_img3/800/600',256),'image','products/prod49/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000049','-',''))),
-- Product 50
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000148','-','')),'https://picsum.photos/seed/prod50_img1/800/600',SHA2('https://picsum.photos/seed/prod50_img1/800/600',256),'image','products/prod50/img1',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000050','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000149','-','')),'https://picsum.photos/seed/prod50_img2/800/600',SHA2('https://picsum.photos/seed/prod50_img2/800/600',256),'image','products/prod50/img2',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000050','-',''))),
(UNHEX(REPLACE('c1000000-0000-0000-0000-000000000150','-','')),'https://picsum.photos/seed/prod50_img3/800/600',SHA2('https://picsum.photos/seed/prod50_img3/800/600',256),'image','products/prod50/img3',UNHEX(REPLACE('b1000000-0000-0000-0000-000000000050','-','')));



INSERT INTO inventories
  (id, product_id, user_id, available_quantity, reserved_quantity, sold_quantity, initial_quantity)
VALUES
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000001','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000001','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,100,0,0,100),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000002','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000002','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,50 ,0,0,50 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000003','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000003','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,200,0,0,200),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000004','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000004','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,150,0,0,150),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000005','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000005','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,75 ,0,0,75 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000006','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000006','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,300,0,0,300),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000007','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000007','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,120,0,0,120),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000008','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000008','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,90 ,0,0,90 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000009','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000009','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,60 ,0,0,60 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000010','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000010','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,40 ,0,0,40 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000011','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000011','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,80 ,0,0,80 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000012','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000012','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,35 ,0,0,35 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000013','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000013','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,110,0,0,110),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000014','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000014','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,250,0,0,250),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000015','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000015','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,30 ,0,0,30 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000016','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000016','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,45 ,0,0,45 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000017','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000017','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,500,0,0,500),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000018','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000018','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,20 ,0,0,20 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000019','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000019','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,60 ,0,0,60 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000020','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000020','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,180,0,0,180),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000021','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000021','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,400,0,0,400),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000022','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000022','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,350,0,0,350),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000023','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000023','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,320,0,0,320),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000024','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000024','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,200,0,0,200),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000025','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000025','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,280,0,0,280),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000026','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000026','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,150,0,0,150),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000027','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000027','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,95 ,0,0,95 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000028','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000028','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,130,0,0,130),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000029','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000029','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,175,0,0,175),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000030','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000030','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,55 ,0,0,55 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000031','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000031','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,70 ,0,0,70 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000032','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000032','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,85 ,0,0,85 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000033','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000033','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,60 ,0,0,60 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000034','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000034','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,45 ,0,0,45 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000035','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000035','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,120,0,0,120),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000036','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000036','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,65 ,0,0,65 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000037','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000037','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,90 ,0,0,90 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000038','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000038','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,400,0,0,400),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000039','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000039','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,75 ,0,0,75 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000040','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000040','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,200,0,0,200),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000041','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000041','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,110,0,0,110),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000042','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000042','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,40 ,0,0,40 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000043','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000043','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,25 ,0,0,25 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000044','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000044','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,30 ,0,0,30 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000045','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000045','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,160,0,0,160),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000046','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000046','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,50 ,0,0,50 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000047','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000047','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,35 ,0,0,35 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000048','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000048','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,80 ,0,0,80 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000049','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000049','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,20 ,0,0,20 ),
(UNHEX(REPLACE('d1000000-0000-0000-0000-000000000050','-','')),UNHEX(REPLACE('b1000000-0000-0000-0000-000000000050','-','')),UNHEX(REPLACE('c14b50fc-196b-468a-a64c-6dc19243bb44','-','')) ,220,0,0,220);

