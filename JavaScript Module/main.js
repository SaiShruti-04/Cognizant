console.log("Welcome to the Community Portal");

window.onload = () => {
  alert("Page is fully loaded");
  init();
};

const events = [
  { id: 1, name: "Music Night", date: "2025-06-15", seats: 5, category: "Music" },
  { id: 2, name: "Baking Workshop", date: "2025-07-01", seats: 10, category: "Workshop" },
  { id: 3, name: "Community Run", date: "2023-05-01", seats: 0, category: "Sports" }, // past or full
  { id: 4, name: "Jazz Concert", date: "2025-06-20", seats: 3, category: "Music" },
];

const today = new Date().toISOString().split("T")[0];
let filteredEvents = [...events];

// Helper: check if event is upcoming and has seats
function isValidEvent(event) {
  return event.date >= today && event.seats > 0;
}

// Render events in #eventsContainer
function renderEvents(eventList) {
  const container = document.querySelector("#eventsContainer");
  container.innerHTML = "";
  if (eventList.length === 0) {
    container.textContent = "No events to display.";
    return;
  }
  eventList.forEach(event => {
    const card = document.createElement("div");
    card.className = "event-card";
    card.innerHTML = `
      <h3>${event.name}</h3>
      <p>Date: ${event.date}</p>
      <p>Category: ${event.category}</p>
      <p>Seats available: ${event.seats}</p>
      <button ${event.seats <= 0 ? 'disabled' : ''} onclick="registerUser(${event.id})">Register</button>
    `;
    container.appendChild(card);
  });
}

// Register user for event by ID
function registerUser(eventId) {
  try {
    const event = events.find(e => e.id === eventId);
    if (!event) throw new Error("Event not found");
    if (event.seats <= 0) throw new Error("No seats available");
    event.seats--;
    alert(`Registered for ${event.name}. Seats left: ${event.seats}`);
    applyFilters(); // re-render with updated seats
    populateEventSelect();
  } catch (error) {
    alert("Registration failed: " + error.message);
  }
}

// Populate event select dropdown in form with valid events
function populateEventSelect() {
  const select = document.querySelector("form#registerForm select[name='event']");
  select.innerHTML = ""; // clear
  const validEvents = events.filter(isValidEvent);
  validEvents.forEach(event => {
    const option = document.createElement("option");
    option.value = event.id;
    option.textContent = `${event.name} (${event.date}) - Seats: ${event.seats}`;
    select.appendChild(option);
  });
  if (validEvents.length === 0) {
    const option = document.createElement("option");
    option.textContent = "No events available";
    option.disabled = true;
    select.appendChild(option);
  }
}

// Filter events by category and search input
function applyFilters() {
  const category = document.querySelector("#categoryFilter").value;
  const search = document.querySelector("#searchInput").value.toLowerCase();

  filteredEvents = events.filter(event => {
    if (!isValidEvent(event)) return false;
    if (category && event.category !== category) return false;
    if (search && !event.name.toLowerCase().includes(search)) return false;
    return true;
  });

  renderEvents(filteredEvents);
}

function init() {
  populateEventSelect();
  applyFilters();

  document.querySelector("#categoryFilter").addEventListener("change", applyFilters);
  document.querySelector("#searchInput").addEventListener("keydown", (e) => {
    if (e.key === "Enter") {
      e.preventDefault();
      applyFilters();
    }
  });

  document.querySelector("#registerForm").addEventListener("submit", function(e) {
    e.preventDefault();
    const form = e.target;
    const name = form.name.value.trim();
    const email = form.email.value.trim();
    const eventId = parseInt(form.event.value);

    if (!name || !email || !eventId) {
      document.getElementById("error").textContent = "All fields are required.";
      return;
    }
    document.getElementById("error").textContent = "";

    const event = events.find(ev => ev.id === eventId);
    if (!event || event.seats <= 0) {
      alert("Selected event is no longer available.");
      populateEventSelect();
      applyFilters();
      return;
    }

    // Simulate AJAX registration with fetch and setTimeout
    document.getElementById("loader").style.display = "block";

    setTimeout(() => {
      fetch("https://jsonplaceholder.typicode.com/posts", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({ name, email, eventId }),
      })
      .then(res => {
        document.getElementById("loader").style.display = "none";
        if (res.ok) {
          alert(`Thanks ${name}, you are registered for ${event.name}!`);
          registerUser(eventId); // reduce seat count and update UI
          form.reset();
        } else {
          alert("Registration failed. Please try again.");
        }
      })
      .catch(() => {
        document.getElementById("loader").style.display = "none";
        alert("Network error. Please try again.");
      });
    }, 1000);
  });
}
