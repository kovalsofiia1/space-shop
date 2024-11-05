package com.cats.spaceshop.service.impl;

import com.cats.spaceshop.domain.product.Product;
import com.cats.spaceshop.dto.MyApiResponse;
import com.cats.spaceshop.dto.product.ProductCreateDto;
import com.cats.spaceshop.dto.product.ProductDetailsDto;
import com.cats.spaceshop.service.ProductService;
import com.cats.spaceshop.service.exception.ProductNotFoundException;
import com.cats.spaceshop.service.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    List<Product> products = new ArrayList<>();

    private static final UUID GALACTIC_CATNIP_ID = UUID.fromString("6e665f4e-e09a-4bde-80c2-8c62f949a75d");
    private static final UUID STELLAR_LASER_POINTER_ID = UUID.fromString("89227cb7-2635-4ef2-bbba-d5ecf9b19bc2");
    private static final UUID NEBULA_SCRATCHER_ID = UUID.fromString("9933dc64-0425-4429-beb5-c4e659961c51");
    private static final UUID COMET_FEATHER_WAND_ID = UUID.fromString("6cd7356e-8490-4e47-b58e-4e6ed8b256c3");
    private static final UUID LUNAR_DUST_LITTER_ID = UUID.fromString("c4c55f7f-1ec7-4bbf-bcd2-5c105906588c");
    private static final UUID ASTRO_BED_CAPSULE_ID = UUID.fromString("afae1a77-1eeb-4d8d-a11b-fcddbc97d4af");
    private static final UUID METEOR_SNACK_PACK_ID = UUID.fromString("f7922044-1fd8-4c70-9135-d5e95ef07b92");
    private static final UUID SOLAR_POWERED_FOUNTAIN_ID = UUID.fromString("dcb4fa05-fc73-45f5-b28d-342b5bb9531f");
    private static final UUID GALAXY_GROOMING_KIT_ID = UUID.fromString("c5793d12-32dc-4725-9b9d-982b56b53f78");
    private static final UUID ORBIT_BALL_ID = UUID.fromString("29b32a89-37f4-4da4-9447-21e1f89a3e59");


    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;

        initializeProducts();
    }


    private void initializeProducts() {
        products.add(Product.builder()
                .productId(GALACTIC_CATNIP_ID)
                .name("Galactic Catnip Whiskers")
                .description("A 100% organic catnip harvested from the lush fields of Planet Felinea.")
                .price(new BigDecimal("12.99"))
                .stockQuantity(100)
                .sku("GCW-001")
                .categoryId("1")
                .build());

        products.add(Product.builder()
                .productId(STELLAR_LASER_POINTER_ID)
                .name("Stellar Laser Pointer")
                .description("Laser pointer with starlight precision, ideal for space training exercises.")
                .price(new BigDecimal("15.49"))
                .stockQuantity(50)
                .sku("SLP-002")
                .categoryId("1")
                .build());

        products.add(Product.builder()
                .productId(NEBULA_SCRATCHER_ID)
                .name("Nebula Scratcher Post")
                .description("A cosmic scratcher post made from asteroid fibers to satisfy intergalactic claw needs.")
                .price(new BigDecimal("35.99"))
                .stockQuantity(30)
                .sku("NSP-003")
                .categoryId("1")
                .build());

        products.add(Product.builder()
                .productId(COMET_FEATHER_WAND_ID)
                .name("Comet Feather Wand")
                .description("Feather wand with comet-like movement for boundless feline fun.")
                .price(new BigDecimal("9.99"))
                .stockQuantity(150)
                .sku("CFW-004")
                .categoryId("2")
                .build());

        products.add(Product.builder()
                .productId(LUNAR_DUST_LITTER_ID)
                .name("Lunar Dust Litter")
                .description("Fine lunar dust for a zero-gravity litter experience, soft and gentle.")
                .price(new BigDecimal("25.00"))
                .stockQuantity(200)
                .sku("LDL-005")
                .categoryId("2")
                .build());

        products.add(Product.builder()
                .productId(ASTRO_BED_CAPSULE_ID)
                .name("Astro Bed Capsule")
                .description("Comfortable and cozy bed capsule designed for deep space naps.")
                .price(new BigDecimal("49.99"))
                .stockQuantity(40)
                .sku("ABC-006")
                .categoryId("2")
                .build());

        products.add(Product.builder()
                .productId(METEOR_SNACK_PACK_ID)
                .name("Meteor Snack Pack")
                .description("A collection of intergalactic treats with flavors from beyond the Milky Way.")
                .price(new BigDecimal("19.99"))
                .stockQuantity(120)
                .sku("MSP-007")
                .categoryId("2")
                .build());

        products.add(Product.builder()
                .productId(SOLAR_POWERED_FOUNTAIN_ID)
                .name("Solar Powered Water Fountain")
                .description("Water fountain that uses solar energy from distant stars to keep your feline hydrated.")
                .price(new BigDecimal("39.99"))
                .stockQuantity(60)
                .sku("SPWF-008")
                .categoryId("2")
                .build());

        products.add(Product.builder()
                .productId(GALAXY_GROOMING_KIT_ID)
                .name("Galaxy Grooming Kit")
                .description("A grooming kit with brushes made from nebula dust to keep fur smooth and shiny.")
                .price(new BigDecimal("29.99"))
                .stockQuantity(75)
                .sku("GGK-009")
                .categoryId("2")
                .build());

        products.add(Product.builder()
                .productId(ORBIT_BALL_ID)
                .name("Orbit Ball")
                .description("A gravity-defying ball that keeps rolling for infinite fun in any cosmic space.")
                .price(new BigDecimal("7.99"))
                .stockQuantity(200)
                .sku("OB-010")
                .categoryId("3")
                .build());
    }

    @Override
    public List<ProductDetailsDto> findAll() {
        return new ArrayList<ProductDetailsDto>(productMapper.toDtoList(products));
    }

    @Override
    public Optional<ProductDetailsDto> findById(UUID id) {
        return Optional.ofNullable(productMapper.toDtoList(products).stream()
                .filter(product -> product.getProductId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id)));
    }

    @Override
    public ProductDetailsDto save(ProductCreateDto productCreateDto) {
        Product product = productMapper.toEntity(productCreateDto);
        products.add(product);
        return productMapper.toDto(product);
    }

    @Override
    public ProductDetailsDto update(ProductDetailsDto product) {
        if (product == null || product.getProductId() == null) {
            throw new IllegalArgumentException("Invalid product data");
        }

        Product existingProduct = products.stream()
                .filter(prod -> prod.getProductId().equals(product.getProductId()))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product not found for update: " + product.getProductId()));

        Product updatedProduct = Product.builder()
                .productId(existingProduct.getProductId()) // Retain the original ID
                .name(product.getName() != null ? product.getName() : existingProduct.getName())
                .description(product.getDescription() != null ? product.getDescription() : existingProduct.getDescription())
                .price(product.getPrice() != null ? product.getPrice() : existingProduct.getPrice())
                .stockQuantity(product.getStockQuantity() != null ? product.getStockQuantity() : existingProduct.getStockQuantity())
                .categoryId(product.getCategoryId() != null ? product.getCategoryId() : existingProduct.getCategoryId())
                .build();

        products.remove(existingProduct);
        products.add(updatedProduct);

        return productMapper.toDto(updatedProduct);
    }

    @Override
    public void deleteById(UUID productId) {
        boolean removed = products.removeIf(product -> product.getProductId().equals(productId));
        if (!removed) {
            throw new ProductNotFoundException("Product not found for deletion: " + productId);
        }
    }

    @Override
    public Optional<List<ProductDetailsDto>> findByCategory(String categoryId) {
        List<ProductDetailsDto> filteredProducts = productMapper.toDtoList(products)
                .stream()
                .filter(product -> product.getCategoryId().equals(categoryId))
                .collect(Collectors.toList());
        return Optional.of(filteredProducts);
    }
}
