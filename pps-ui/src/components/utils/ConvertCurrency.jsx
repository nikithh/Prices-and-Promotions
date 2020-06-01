let currencyData = fetch("https://api.exchangeratesapi.io/latest")
  .then((response) => response.json())
  .then((data) => {
    currencyData = data
  })

// eslint-disable-next-line consistent-return
export default function convertCurrency(from, to, amt) {
  if (from !== "EUR")
    return new Intl.NumberFormat("de-DE", {
      style: "currency",
      currency: "EUR",
      maximumSignificantDigits: 3,
    }).format(parseFloat(amt) / parseFloat(currencyData.rates[from]))
}
