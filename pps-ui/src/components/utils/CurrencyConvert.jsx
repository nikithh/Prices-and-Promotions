let currencyData = fetch("https://api.exchangeratesapi.io/latest")
  .then((response) => response.json())
  .then((data) => {
    currencyData = data
  })

export default function currencyConvert(from, amt) {
  return (parseFloat(amt) / currencyData.rates[from]) * currencyData.rates.USD
}
