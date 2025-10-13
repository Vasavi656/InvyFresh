const baseUrl = "/api/products";

async function loadDashboard() {
  const res = await fetch(baseUrl);
  const products = await res.json();

  document.getElementById("totalProducts").innerText = products.length;
  document.getElementById("lowStock").innerText =
    products.filter(p => p.stock < p.minStockLevel).length;
  document.getElementById("expiringSoon").innerText =
    products.filter(
      p =>
        new Date(p.expiryDate) <
        new Date(Date.now() + 7 * 24 * 60 * 60 * 1000)
    ).length;
}

window.onload = loadDashboard;
