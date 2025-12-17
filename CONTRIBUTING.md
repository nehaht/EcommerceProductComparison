# Contributing to E-commerce Product Comparison Tool

Thank you for your interest in contributing! Here's how you can help:

## How to Contribute

1. **Fork the repository**
2. **Create a feature branch**: `git checkout -b feature/new-scraper`
3. **Make your changes**
4. **Test your changes**
5. **Commit**: `git commit -m "Add new scraper for XYZ site"`
6. **Push**: `git push origin feature/new-scraper`
7. **Create a Pull Request**

## Adding New E-commerce Sites

1. Create a new scraper class implementing `EcommerceScraper` interface
2. Add robust selector fallbacks in your scraper
3. Add the scraper to `ProductComparisonService`
4. Test with different products
5. Update README with the new site

## Code Style

- Use meaningful variable names
- Add comments for complex logic
- Follow existing code patterns
- Include error handling

## Testing

- Test with various product searches
- Verify all sites return results
- Check error handling works properly

## Issues

- Report bugs with detailed steps to reproduce
- Suggest new features with use cases
- Help improve documentation