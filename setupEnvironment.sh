# Step One- Fill out the UNIT_TWO_REPO_NAME and GITHUB_USERNAME

# Step Two - configure your shell to always have these variables.
# For OSX / Linux
# Copy and paste ALL of the properties below into your .bash_profile in your home directly

# For Windows
# Copy and paste ALL of the properties below into your .bashrc file in your home directory

export GITHUB_TOKEN='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'
export GITHUB_USERNAME=ericamuse95

export UNIT_THREE_REPO_NAME=ata-unit-three-project-$GITHUB_USERNAME

# Do not modify the rest of these unless you have been instructed to do so.
export UNIT_THREE_PROJECT_NAME=unitproject3
export UNIT_THREE_PIPELINE_STACK=$UNIT_THREE_PROJECT_NAME-$GITHUB_USERNAME
export UNIT_THREE_ARTIFACT_BUCKET=$UNIT_THREE_PROJECT_NAME-$GITHUB_USERNAME-artifacts
export UNIT_THREE_DEPLOY_STACK=$UNIT_THREE_PROJECT_NAME-$GITHUB_USERNAME-application
