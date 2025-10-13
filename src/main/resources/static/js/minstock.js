document.addEventListener("DOMContentLoaded", async () => {
  const tableBody = document.querySelector("#minStockTable");
  const chartCanvas = document.getElementById("minStockChart");
  let chartInstance = null;

  async function loadMinStockData() {
    try {
      const response = await fetch("/api/products");
      const products = await response.json();

      tableBody.innerHTML = "";

      products.forEach(prod => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${prod.id}</td>
          <td>${prod.name}</td>
          <td>${prod.stock}</td>
          <td>${prod.minStockLevel}</td>
        `;
        tableBody.appendChild(row);
      });

      const labels = products.map(p => p.name);
      const quantities = products.map(p => p.stock);

      const colors = products.map(p => {
        if (p.stock < p.minStockLevel) return "rgba(255, 99, 132, 0.7)"; // red
        if (p.stock === p.minStockLevel) return "rgba(54, 162, 235, 0.7)"; // blue
        return "rgba(75, 192, 192, 0.7)"; // green
      });

      if (chartInstance) chartInstance.destroy();

      chartInstance = new Chart(chartCanvas, {
        type: "bar",
        data: {
          labels: labels,
          datasets: [{
            label: "Stock Quantity",
            data: quantities,
            backgroundColor: colors,
            borderColor: colors.map(c => c.replace("0.7", "1")),
            borderWidth: 1,
            barThickness: 25
          }]
        },
        options: {
          responsive: true,
          plugins: {
            legend: { display: false },
            title: {
              display: true,
              text: "Product Stock Levels",
              font: { size: 16 }
            }
          },
          scales: { y: { beginAtZero: true } }
        }
      });

    } catch (error) {
      console.error("Error fetching products:", error);
    }
  }

  loadMinStockData();
  setInterval(loadMinStockData, 10000); 
});
