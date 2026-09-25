import { useEffect, useState } from "react";
import "./App.css";

const API_URL = "http://localhost:8080/applications";

const emptyForm = {
  company: "",
  position: "",
  status: "Applied",
  dateApplied: "",
  deadline: "",
  location: "",
  jobUrl: "",
  notes: "",
};

function App() {
  const [applications, setApplications] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [editingId, setEditingId] = useState(null);
  const [formData, setFormData] = useState(emptyForm);

  // NEW: Search and filter
  const [searchTerm, setSearchTerm] = useState("");
  const [statusFilter, setStatusFilter] = useState("All");

  useEffect(() => {
    loadApplications();
  }, []);

  const loadApplications = async () => {
    try {
      const response = await fetch(API_URL);

      if (!response.ok) {
        throw new Error("Could not load applications");
      }

      const data = await response.json();
      setApplications(data);
    } catch (error) {
      console.error("Error loading applications:", error);
    }
  };

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  const openAddForm = () => {
    setEditingId(null);
    setFormData(emptyForm);
    setShowForm(true);
  };

  const openEditForm = (application) => {
    setEditingId(application.id);

    setFormData({
      company: application.company || "",
      position: application.position || "",
      status: application.status || "Applied",
      dateApplied: application.dateApplied || "",
      deadline: application.deadline || "",
      location: application.location || "",
      jobUrl: application.jobUrl || "",
      notes: application.notes || "",
    });

    setShowForm(true);
  };

  const closeForm = () => {
    setShowForm(false);
    setEditingId(null);
    setFormData(emptyForm);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const applicationToSave = {
      ...formData,
      dateApplied: formData.dateApplied || null,
      deadline: formData.deadline || null,
    };

    try {
      const url = editingId
        ? `${API_URL}/${editingId}`
        : API_URL;

      const method = editingId ? "PUT" : "POST";

      const response = await fetch(url, {
        method,
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(applicationToSave),
      });

      if (!response.ok) {
        throw new Error("Could not save application");
      }

      await loadApplications();
      closeForm();
    } catch (error) {
      console.error("Error saving application:", error);
    }
  };

  const deleteApplication = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this application?"
    );

    if (!confirmed) {
      return;
    }

    try {
      const response = await fetch(`${API_URL}/${id}`, {
        method: "DELETE",
      });

      if (!response.ok) {
        throw new Error("Could not delete application");
      }

      await loadApplications();
    } catch (error) {
      console.error("Error deleting application:", error);
    }
  };

  // SEARCH + FILTER
  const filteredApplications = applications.filter((application) => {
    const search = searchTerm.toLowerCase();

    const matchesSearch =
      application.company?.toLowerCase().includes(search) ||
      application.position?.toLowerCase().includes(search) ||
      application.location?.toLowerCase().includes(search);

    const matchesStatus =
      statusFilter === "All" ||
      application.status === statusFilter;

    return matchesSearch && matchesStatus;
  });

  return (
    <div className="app">

      {/* HEADER */}

      <header className="header">
        <div>
          <h1>Co-op Application Tracker</h1>
          <p>Track your applications, interviews, and offers.</p>
        </div>

        <button className="add-button" onClick={openAddForm}>
          + Add Application
        </button>
      </header>

      {/* STATISTICS */}

      <section className="stats">

        <div className="stat-card">
          <span>Total Applications</span>
          <strong>{applications.length}</strong>
        </div>

        <div className="stat-card">
          <span>Applied</span>
          <strong>
            {
              applications.filter(
                (application) => application.status === "Applied"
              ).length
            }
          </strong>
        </div>

        <div className="stat-card">
          <span>Interviews</span>
          <strong>
            {
              applications.filter(
                (application) => application.status === "Interview"
              ).length
            }
          </strong>
        </div>

        <div className="stat-card">
          <span>Offers</span>
          <strong>
            {
              applications.filter(
                (application) => application.status === "Offer"
              ).length
            }
          </strong>
        </div>

      </section>

      {/* APPLICATIONS */}

      <section className="applications-section">

        <div className="section-header">
          <div>
            <h2>Applications</h2>
            <span>
              Showing {filteredApplications.length} of{" "}
              {applications.length}
            </span>
          </div>
        </div>

        {/* SEARCH + FILTER */}

        <div className="filters">

          <input
            className="search-input"
            type="text"
            placeholder="Search company, position, or location..."
            value={searchTerm}
            onChange={(event) =>
              setSearchTerm(event.target.value)
            }
          />

          <select
            className="filter-select"
            value={statusFilter}
            onChange={(event) =>
              setStatusFilter(event.target.value)
            }
          >
            <option value="All">All Statuses</option>
            <option value="Applied">Applied</option>
            <option value="Interview">Interview</option>
            <option value="Offer">Offer</option>
            <option value="Rejected">Rejected</option>
            <option value="Withdrawn">Withdrawn</option>
          </select>

        </div>

        {filteredApplications.length === 0 ? (

          <div className="empty-state">
            <h3>No applications found</h3>
            <p>Try changing your search or status filter.</p>
          </div>

        ) : (

          <div className="table-container">

            <table>

              <thead>
                <tr>
                  <th>Company</th>
                  <th>Position</th>
                  <th>Status</th>
                  <th>Date Applied</th>
                  <th>Location</th>
                  <th>Actions</th>
                </tr>
              </thead>

              <tbody>

                {filteredApplications.map((application) => (

                  <tr key={application.id}>

                    <td className="company">
                      {application.company}
                    </td>

                    <td>{application.position}</td>

                    <td>
                      <span
                        className={`status ${application.status
                          ?.toLowerCase()
                          .replace(" ", "-")}`}
                      >
                        {application.status}
                      </span>
                    </td>

                    <td>{application.dateApplied || "—"}</td>

                    <td>{application.location || "—"}</td>

                    <td>
                      <div className="action-buttons">

                        <button
                          className="edit-button"
                          onClick={() =>
                            openEditForm(application)
                          }
                        >
                          Edit
                        </button>

                        <button
                          className="delete-button"
                          onClick={() =>
                            deleteApplication(application.id)
                          }
                        >
                          Delete
                        </button>

                      </div>
                    </td>

                  </tr>

                ))}

              </tbody>

            </table>

          </div>

        )}

      </section>

      {/* ADD / EDIT MODAL */}

      {showForm && (

        <div className="modal-overlay">

          <div className="modal">

            <div className="modal-header">

              <div>
                <h2>
                  {editingId
                    ? "Edit Application"
                    : "Add Application"}
                </h2>

                <p>
                  {editingId
                    ? "Update your application information."
                    : "Add a new co-op application to your tracker."}
                </p>
              </div>

              <button
                className="close-button"
                onClick={closeForm}
              >
                ×
              </button>

            </div>

            <form onSubmit={handleSubmit}>

              <div className="form-grid">

                <div className="form-group">
                  <label>Company *</label>

                  <input
                    type="text"
                    name="company"
                    value={formData.company}
                    onChange={handleChange}
                    placeholder="e.g. Nokia"
                    required
                  />
                </div>

                <div className="form-group">
                  <label>Position *</label>

                  <input
                    type="text"
                    name="position"
                    value={formData.position}
                    onChange={handleChange}
                    placeholder="e.g. Software Developer Co-op"
                    required
                  />
                </div>

                <div className="form-group">
                  <label>Status</label>

                  <select
                    name="status"
                    value={formData.status}
                    onChange={handleChange}
                  >
                    <option value="Applied">Applied</option>
                    <option value="Interview">Interview</option>
                    <option value="Offer">Offer</option>
                    <option value="Rejected">Rejected</option>
                    <option value="Withdrawn">Withdrawn</option>
                  </select>
                </div>

                <div className="form-group">
                  <label>Location</label>

                  <input
                    type="text"
                    name="location"
                    value={formData.location}
                    onChange={handleChange}
                    placeholder="e.g. Ottawa, ON"
                  />
                </div>

                <div className="form-group">
                  <label>Date Applied</label>

                  <input
                    type="date"
                    name="dateApplied"
                    value={formData.dateApplied}
                    onChange={handleChange}
                  />
                </div>

                <div className="form-group">
                  <label>Deadline</label>

                  <input
                    type="date"
                    name="deadline"
                    value={formData.deadline}
                    onChange={handleChange}
                  />
                </div>

              </div>

              <div className="form-group">
                <label>Job URL</label>

                <input
                  type="url"
                  name="jobUrl"
                  value={formData.jobUrl}
                  onChange={handleChange}
                  placeholder="https://..."
                />
              </div>

              <div className="form-group">
                <label>Notes</label>

                <textarea
                  name="notes"
                  value={formData.notes}
                  onChange={handleChange}
                  placeholder="Interview details, contacts, follow-up notes..."
                  rows="4"
                />
              </div>

              <div className="form-actions">

                <button
                  type="button"
                  className="cancel-button"
                  onClick={closeForm}
                >
                  Cancel
                </button>

                <button
                  type="submit"
                  className="save-button"
                >
                  {editingId
                    ? "Save Changes"
                    : "Save Application"}
                </button>

              </div>

            </form>

          </div>

        </div>

      )}

    </div>
  );
}

export default App;