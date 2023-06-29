describe('editing signs', () => {
  beforeEach(() => {
    cy.deleteUsers();
    cy.createTestAdmin();
  })
  it('edit sign with image with new image', () => {
    cy.login()
      .then((response) => {
        const token = response.body.token;
        cy.createSignwithImg(token).then(({ body }) => {
          const bodyString = Cypress.Blob.arrayBufferToBinaryString(body);
          const bodyJson = JSON.parse(bodyString);
          const signId = bodyJson.id
          cy.fixture("tree.png", 'binary')
            .then((file) => Cypress.Blob.binaryStringToBlob(file))
            .then((blob) => {
              const formData = new FormData();
              formData.append("signImage", blob, "tree.png");
              formData.append("signId", signId);
              formData.append("title", "tree");
              formData.append("description", "x");
              formData.append("lat", 1.0);
              formData.append("lon", 1.0);
              cy.request({
                url: `/signs/${signId}`,
                method: "PATCH",
                headers: {
                  Authorization: `bearer ${token}`,
                  'content-type': 'multipart/form-data'
                },
                body: formData
              }).then(({ status }) => {
                expect(status).to.eq(200)
              })
            })
        })
      })
  })

  it('edit sign without image with new image', () => {
    cy.login()
      .then((response) => {
        const token = response.body.token;
        cy.createSignwithoutImg(token).then(({ body }) => {
          const bodyString = Cypress.Blob.arrayBufferToBinaryString(body);
          const bodyJson = JSON.parse(bodyString);
          const signId = bodyJson.id
          cy.fixture("tree.png", 'binary')
            .then((file) => Cypress.Blob.binaryStringToBlob(file))
            .then((blob) => {
              const formData = new FormData();
              formData.append("signImage", blob, "tree.png");
              formData.append("signId", signId);
              formData.append("title", "tree");
              formData.append("description", "x");
              formData.append("lat", 1.0);
              formData.append("lon", 1.0);
              cy.request({
                url: `/signs/${signId}`,
                method: "PATCH",
                headers: {
                  Authorization: `bearer ${token}`,
                  'content-type': 'multipart/form-data'
                },
                body: formData
              }).then(({ status }) => {
                expect(status).to.eq(200)
              })
            })
        })
      })
  })

  it('edit sign with image without new image', () => {
    cy.login()
      .then((response) => {
        const token = response.body.token;
        cy.createSignwithImg(token).then(({ body }) => {
          const bodyString = Cypress.Blob.arrayBufferToBinaryString(body);
          const bodyJson = JSON.parse(bodyString);
          const signId = bodyJson.id
          const emptyBlob = new Blob([])
          const formData = new FormData();
          formData.append("signImage", emptyBlob, "");
          formData.append("signId", signId);
          formData.append("title", "tree");
          formData.append("description", "x");
          formData.append("lat", 1.0);
          formData.append("lon", 1.0);
          cy.request({
            url: `/signs/${signId}`,
            method: "PATCH",
            headers: {
              Authorization: `bearer ${token}`,
              'content-type': 'multipart/form-data'
            },
            body: formData
          }).then(({ status }) => {
            expect(status).to.eq(200)
          })
        })
      })
  })

  it('edit sign without image without new image', () => {
    cy.login()
      .then((response) => {
        const token = response.body.token;
        cy.createSignwithoutImg(token).then(({ body }) => {
          const bodyString = Cypress.Blob.arrayBufferToBinaryString(body);
          const bodyJson = JSON.parse(bodyString);
          const signId = bodyJson.id
          const emptyBlob = new Blob([])
          const formData = new FormData();
          formData.append("signImage", emptyBlob, "");
          formData.append("signId", signId);
          formData.append("title", "tree");
          formData.append("description", "x");
          formData.append("lat", 1.0);
          formData.append("lon", 1.0);
          cy.request({
            url: `/signs/${signId}`,
            method: "PATCH",
            headers: {
              Authorization: `bearer ${token}`,
              'content-type': 'multipart/form-data'
            },
            body: formData
          }).then(({ status }) => {
            expect(status).to.eq(200)
          })
        })
      })
  })

})
