describe('adding new signs', () => {
  beforeEach(() => {
    cy.deleteUsers();
    cy.createTestAdmin();
  })
  it('add new sign with an image', () => {
    cy.login()
      .then((response) => {
        const token = response.body.token;
        cy.fixture("tree.png", 'binary')
          .then((file) => Cypress.Blob.binaryStringToBlob(file))
          .then((blob) => {
            const formData = new FormData();
            formData.append("signImage", blob, "tree.png");
            formData.append("title", "tree");
            formData.append("description", "x");
            formData.append("lat", 1.0);
            formData.append("lon", 1.0);
            cy.request({
              url: "/signs",
              method: "POST",
              headers: {
                Authorization: `bearer ${token}`,
                'content-type': 'multipart/form-data'
              },
              body: formData
            }).then(({ status }) => {
              expect(status).to.eq(201)
            })
          })
      })
  })
  it('add new sign without image', () => {
    cy.login()
      .then((response) => {
        const token = response.body.token;
        const formData = new FormData();
        const emptyBlob = new Blob([])
        formData.append("signImage", emptyBlob, "");
        formData.append("title", "tree");
        formData.append("description", "x");
        formData.append("lat", 1.0);
        formData.append("lon", 1.0);
        cy.request({
          url: "/signs",
          method: "POST",
          headers: {
            Authorization: `bearer ${token}`,
          },
          body: formData
        }).then(({ status }) => {
          expect(status).to.eq(201)
        })
      })
  })
})
